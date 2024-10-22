package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    private User saveUser;
    private Item saveItem1;
    private Item saveItem2;

    @BeforeEach
    void setUp() {
        saveUser = userService.createUser(new User("김태현", 0, LocalDateTime.now()));
        saveItem1 = itemService.createItem(new Item("청바지1", 40000, 5, LocalDateTime.now()));
        saveItem2 = itemService.createItem(new Item("청바지2", 20000, 5, LocalDateTime.now()));
    }

    @Test
    @DisplayName("주문_생성")
    void createOrder() {
        //given
        Order order = new Order(saveUser, OrderStatus.ORDER, LocalDateTime.now());
        OrderDetail orderDetail = new OrderDetail(order, saveItem1, 2, saveItem1.getItemPrice());
        order.addOrderDetail(orderDetail);
        //when
        Order resultOrder = orderService.createOrder(order);
        //then
        assertNotNull(resultOrder);
        assertEquals(resultOrder.getOrderUser().getUserId(), saveUser.getUserId());
    }

    @Test
    @DisplayName("사용자의_주문_조회")
    void getOrderByUserId() {
        //given
        Order order = new Order(saveUser, OrderStatus.ORDER, LocalDateTime.now());
        Order createOrder = orderService.createOrder(order);
        //when
        Order resultOrder = orderService.getOrderByUser(saveUser);
        //then
        assertNotNull(resultOrder);
        assertEquals(resultOrder.getOrderUser().getUserId(), saveUser.getUserId());
        assertEquals(resultOrder.getOrderId(), createOrder.getOrderId());
    }

    @Test
    @DisplayName("주문번호로_주문_상세_조회")
    void getDetailByOrderId() {
        //given
        Order order = new Order(saveUser, OrderStatus.ORDER, LocalDateTime.now());
        OrderDetail orderDetail = new OrderDetail(order, saveItem1, 2, saveItem1.getItemPrice());
        order.addOrderDetail(orderDetail);
        Order resultOrder = orderService.createOrder(order);
        //when
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(resultOrder);
        //given
        assertNotNull(orderDetailList);
        assertEquals(orderDetailList.size(), 1);
    }
}