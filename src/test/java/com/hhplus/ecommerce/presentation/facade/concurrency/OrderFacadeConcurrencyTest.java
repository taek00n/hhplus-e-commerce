package com.hhplus.ecommerce.presentation.facade.concurrency;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.facade.OrderFacade;
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

@SpringBootTest
class OrderFacadeConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("재고가 120개인 상품을 30명이 5개씩 구매하면 24명은 성공하고 6명은 실패한다.")
    void concurrencyTest2() throws Exception {
        //given
        Item saveItem = itemRepository.save(new Item("특가세일", 5000, 120, LocalDateTime.now()));

        int thread = 30;
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
                    itemMap.put(saveItem.getItemId(), 5);
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

        //then
        assertEquals(24, successCount.get());
        assertEquals(6, failCount.get());

        executorService.shutdown();
    }

    private User createUser(String username, int balance) {

        return userRepository.save(new User(username, balance, LocalDateTime.now()));
    }
}