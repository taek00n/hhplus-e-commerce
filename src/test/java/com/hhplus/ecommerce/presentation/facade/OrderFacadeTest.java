package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.infrastructure.event.CreateOrderEvent;
import com.hhplus.ecommerce.presentation.dto.request.order.CancelOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.OrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CancelOrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.response.order.OrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RecordApplicationEvents
@SpringBootTest
@Transactional
class OrderFacadeTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private PayFacade payFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ApplicationEvents applicationEvents;


    @Test
    @DisplayName("주문생성")
    void createOrder() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999));
        Item saveItem = itemService.createItem(new Item("후드티", 30000, 4));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        //when
        CreateOrderResponseDto responseDto = orderFacade.createOrder(new CreateOrderRequestDto(saveUser.getUserId(), itemMap));
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.userId(), saveUser.getUserId());
        assertEquals(responseDto.totalPrice(), 30000);
        assertEquals(responseDto.totalAmount(), 1);
        List<CreateOrderEvent> events = applicationEvents.stream(CreateOrderEvent.class).toList();
        assertThat(events).isNotEmpty();
    }

    @Test
    @DisplayName("주문을 취소하였을떄 상품과 포인트가 정상적으로 돌아오는지 확인")
    void cancelOrder() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999));
        Item saveItem = itemService.createItem(new Item("후드티", 3000, 4));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        CreateOrderResponseDto order = orderFacade.createOrder(new CreateOrderRequestDto(saveUser.getUserId(), itemMap));
        //when
        CancelOrderResponseDto cancelOrderResponseDto = orderFacade.cancelOrder(new CancelOrderRequestDto(order.orderId(), order.userId()));
        //then
        assertNotNull(cancelOrderResponseDto);
        assertEquals(cancelOrderResponseDto.orderId(), order.orderId());
        assertEquals(saveUser.getBalance(), 6999);
        assertEquals(saveItem.getItemStock(), 4);
    }

    @Test
    @DisplayName("주문조회")
    void getOrder() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999));
        Item saveItem = itemService.createItem(new Item("후드티", 30000, 4));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        orderFacade.createOrder(new CreateOrderRequestDto(saveUser.getUserId(), itemMap));
        //when
        OrderResponseDto responseDto = orderFacade.getUserOrder(new OrderRequestDto(saveUser.getUserId()));
        //given
        assertNotNull(responseDto);
        assertEquals(responseDto.totalPrice(), 30000);
        assertEquals(responseDto.totalAmount(), 1);
    }

    @Test
    @DisplayName("결제")
    void pay() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999));
        Item saveItem = itemService.createItem(new Item("후드티", 30000, 4));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        CreateOrderResponseDto responseDto = orderFacade.createOrder(new CreateOrderRequestDto(saveUser.getUserId(), itemMap));
        //when
        PayResponseDto payResponseDto = payFacade.receiveOrderToPay(new PayRequestDto(responseDto.orderId()));
        //then
        assertNotNull(payResponseDto);
        assertEquals(payResponseDto.isSuccess(), true);
    }
}