package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Orders;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersService ordersService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        reset(ordersRepository);
        mockUser = new User(1L, "김태현", 0, LocalDateTime.now());
    }

    @Test
    @DisplayName("주문_생성")
    void saveOrders() {
        //given
        Orders mockOrders = new Orders(1L, mockUser, LocalDateTime.now());
        when(ordersRepository.save(mockOrders)).thenReturn(mockOrders);
        //when
        Orders resultOrders = ordersService.save(mockOrders);
        //then
        assertNotNull(resultOrders);
        assertEquals(mockOrders.getOrdersId(), resultOrders.getOrdersId());
        assertEquals(mockOrders.getOrderUser().getUserId(), resultOrders.getOrderUser().getUserId());
    }
}