package com.hhplus.ecommerce.application.unit;

import com.hhplus.ecommerce.application.OrderService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.OrderRepository;
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
class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        reset(orderRepository);
        mockUser = new User("김태현", 0);
    }

    @Test
    @DisplayName("주문_생성")
    void createOrders() {
        //given
        Order mockOrder = new Order(mockUser);
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
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
        Order mockOrder = new Order(mockUser);
        when(orderRepository.findByOrderId(mockOrder.getOrderId())).thenReturn(Optional.of(mockOrder));
        //when
        Order resultOrder = orderService.getOrderByOrderId(mockOrder.getOrderId());
        //then
        assertNotNull(resultOrder);
        assertEquals(mockOrder.getOrderId(), resultOrder.getOrderId());
    }
}