package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        reset(orderRepository);
        mockUser = new User("김태현", 0, LocalDateTime.now());
    }

    @Test
    @DisplayName("주문_생성")
    void createOrders() {
        //given
        Order mockOrder = new Order(mockUser, OrderStatus.ORDER, LocalDateTime.now());
        when(orderService.createOrder(mockOrder)).thenReturn(mockOrder);
        //when
        Order resultOrder = orderService.createOrder(mockOrder);
        //then
        assertNotNull(resultOrder);
        assertEquals(mockOrder.getOrderId(), resultOrder.getOrderId());
        assertEquals(mockOrder.getOrderUser().getUserId(), resultOrder.getOrderUser().getUserId());
    }

    @Test
    @DisplayName("주문_조회")
    void getOrders() {
        //given
        Order mockOrder = new Order(mockUser, OrderStatus.ORDER, LocalDateTime.now());
        when(orderRepository.getOrder(mockOrder.getOrderId())).thenReturn(Optional.of(mockOrder));
        //when
        Order resultOrder = orderService.getOrder(mockOrder.getOrderId());
        //then
        assertNotNull(resultOrder);
        assertEquals(mockOrder.getOrderId(), resultOrder.getOrderId());
    }
}