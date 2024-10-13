package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.*;
import com.hhplus.ecommerce.infrastructure.OrdersDetailRepository;
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
class OrdersDetailServiceTest {

    @Mock
    private OrdersDetailRepository ordersDetailRepository;

    @InjectMocks
    private OrdersDetailService ordersDetailService;

    private Orders mockOrders;
    private User mockUser;
    private Item mockItem;
    private ItemDetail mockItemDetail;

    @BeforeEach
    void setUp() {
        reset(ordersDetailRepository);
        mockUser = new User(1L, "김태현", 0, LocalDateTime.now());
        mockOrders = new Orders(1L, mockUser, LocalDateTime.now());
        mockItem = new Item(1L, "후드티", LocalDateTime.now());
        mockItemDetail = new ItemDetail(1L, mockItem, "후드티A", 4000, 2);
    }

    @Test
    @DisplayName("주문_상세_생성")
    void saveOrdersDetail() {
        //given
        OrdersDetail mockOrdersDetail = new OrdersDetail(1L, mockOrders, mockItemDetail, mockItem.getItemName(), mockItemDetail.getItemPrice(), 1);
        when(ordersDetailRepository.save(mockOrdersDetail)).thenReturn(mockOrdersDetail);
        //when
        OrdersDetail resultOrdersDetail = ordersDetailService.save(mockOrdersDetail);
        //then
        assertNotNull(resultOrdersDetail);
        assertEquals(mockOrdersDetail.getOrdersDetailId(), resultOrdersDetail.getOrdersDetailId());
    }

    @Test
    @DisplayName("주문_상세_조회")
    void getOrdersDetail() {
        //given
        OrdersDetail mockOrdersDetail = new OrdersDetail(1L, mockOrders, mockItemDetail, mockItem.getItemName(), mockItemDetail.getItemPrice(), 1);
        when(ordersDetailRepository.findById(mockOrdersDetail.getOrdersDetailId())).thenReturn(Optional.of(mockOrdersDetail));
        //when
        OrdersDetail resultOrdersDetail = ordersDetailService.getOrdersDetail(mockOrdersDetail.getOrdersDetailId());
        //then
        assertNotNull(resultOrdersDetail);
        assertEquals(mockOrdersDetail.getOrdersDetailId(), resultOrdersDetail.getOrdersDetailId());
    }
}