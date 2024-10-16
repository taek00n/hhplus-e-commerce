package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.domain.Item;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("주문_상위5개_조회")
    void findTopItems() {
        // given
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);

        Item mockItem1 = buildItem(1L, "청바지1", 10000, 90);
        Item mockItem2 = buildItem(2L, "청바지2", 20000, 90);
        Item mockItem3 = buildItem(3L, "청바지3", 30000, 90);
        Item mockItem4 = buildItem(4L, "청바지4", 40000, 90);
        Item mockItem5 = buildItem(5L, "청바지5", 50000, 90);
        Item mockItem6 = buildItem(6L, "청바지6", 60000, 90);
        List<Item> mockTopItemList = List.of(mockItem1, mockItem2, mockItem3, mockItem4, mockItem5);
        when(orderRepository.findTopItems(startDateTime, endDateTime)).thenReturn(mockTopItemList);
        //when
        List<Item> resultTopItem = orderService.findTopItems();
        //then
        assertNotNull(resultTopItem);
        assertEquals(mockTopItemList.size(), resultTopItem.size());
        assertEquals(mockTopItemList.get(0), resultTopItem.get(0));
    }

    private Item buildItem(Long itemId, String itemName, int itemPrice, int itemStock) {
        return Item.builder().itemId(itemId).itemName(itemName).itemPrice(itemPrice).itemStock(itemStock).build();
    }
}