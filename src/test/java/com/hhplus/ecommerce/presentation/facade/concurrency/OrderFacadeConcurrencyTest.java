package com.hhplus.ecommerce.presentation.facade.concurrency;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.OrderDetailService;
import com.hhplus.ecommerce.application.OrderService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import com.hhplus.ecommerce.presentation.dto.request.order.CancelOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.facade.OrderFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class OrderFacadeConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("재고가 120개인 상품을 30명이 5개씩 구매하면 24명은 성공하고 6명은 실패한다. - 분산락으로 진행")
    void orderConcurrencyTest() throws Exception {
        long startTime = System.currentTimeMillis();
        //given
        Item saveItem = itemRepository.save(new Item("특가세일", 5000, 120));

        int thread = 30;
        int amount = 5;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            String userName = "testUser" ;
            User user = createUser(userName, 200000);
            userList.add(user);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < thread; i++) {
            User user = userList.get(i);
            executorService.execute(() -> {
                try {
                    Map<Long, Integer> itemMap = new HashMap<>();
                    itemMap.put(saveItem.getItemId(), amount);
                    CreateOrderRequestDto requestDto = new CreateOrderRequestDto(user.getUserId(), itemMap);
                    orderFacade.createOrder(requestDto);
                    successCount.incrementAndGet();
                } catch (RestApiException e) {
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        //then
        Item item = itemService.getItemByItemId(saveItem.getItemId());
        assertEquals(0, item.getItemStock());
        assertEquals(24, successCount.get());
        assertEquals(6, failCount.get());

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("상품 재고 차감 분산락 소요시간 ==> " + duration + "ms");
    }

    @Test
    @DisplayName("주문을 취소했을떄 상품의 재고가 정상적으로 저장되는것을 확인 - 비관적 락으로 진행")
    void cancelOrderConcurrencyTest() throws Exception {
        long startTime = System.currentTimeMillis();
        //given
        Item saveItem = itemRepository.save(new Item("특가세일", 5000, 120));

        int thread = 10;
        int amount = 5;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            String userName = "testUser" ;
            User user = createUser(userName, 200000);
            userList.add(user);
        }

        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            Order order = new Order(userList.get(i));
            Order saveOrder = orderService.createOrder(order);
            orderList.add(saveOrder);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < thread; i++) {
            Order order = orderList.get(i);
            executorService.execute(() -> {
                try {
                    CancelOrderRequestDto requestDto = new CancelOrderRequestDto(order.getOrderId(), order.getOrderUser().getUserId());
                    orderFacade.cancelOrder(requestDto);
                    successCount.incrementAndGet();
                } catch (RestApiException e) {
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        //then
        Item resultItem = itemService.getItemByItemId(saveItem.getItemId());
        assertEquals(10, successCount.get());
        assertEquals(0, failCount.get());
        assertEquals(saveItem.getItemStock() + (amount * thread), resultItem.getItemStock());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("상품 재고 복원 비관적락 소요시간 ==> " + duration + "ms");
    }

    private User createUser(String username, int balance) {

        return userRepository.save(new User(username, balance));
    }

    @Test
    @DisplayName("카프카 연동 테스트 : 여러개 동시성 테스트")
    void kafkaTest() throws Exception {
        int thread = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        for (int i = 0; i < thread; i++) {
            long userId = i;
            executorService.execute(() -> {
                try {
                    orderFacade.kafkaTest(userId);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }
}