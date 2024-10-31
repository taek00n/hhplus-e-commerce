package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.OrderDetailService;
import com.hhplus.ecommerce.application.OrderService;
import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import com.hhplus.ecommerce.common.exception.domain.OrderErrorCode;
import com.hhplus.ecommerce.presentation.dto.request.order.CancelOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.OrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CancelOrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.response.order.OrderResponseDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    private final UserService userService;
    private final ItemService itemService;

    public OrderResponseDto getUserOrder(OrderRequestDto orderRequestDto) {

        User user = userService.getUserByUserId(orderRequestDto.userId());
        Order order = orderService.getOrderByUser(user);
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(order);

        return new OrderResponseDto(order.getOrderId(), orderDetailList, order.getTotalPrice(), order.getTotalAmount());
    }

    @Transactional
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {

        List<OrderDetail> orderDetails = new ArrayList<>();
        User user = userService.getUserByUserId(requestDto.userId());
        Order order = new Order(user, OrderStatus.ORDER, LocalDateTime.now());

        requestDto.itemMap().forEach((itemId, amount) -> {
            Item item = itemService.getItemByItemIdWithLock(itemId);
            OrderDetail orderDetail = new OrderDetail(order, item, amount, item.getItemPrice());
            orderDetails.add(orderDetail);
//            itemService.reduceItemStockWithRedisson(item, amount);
            itemService.reduceItemStock(item, amount);
        });

        orderDetails.forEach(orderDetail -> {
            order.addOrderDetail(orderDetail);
        });

        Order createOrder = orderService.createOrder(order);

        return new CreateOrderResponseDto(createOrder.getOrderId(), createOrder.getOrderUser().getUserId(), createOrder.getTotalPrice(), createOrder.getTotalAmount());
    }

    @Transactional
    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {

        Order order = orderService.getOrderByOrderId(cancelOrderRequestDto.orderId());

        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(order);
        orderDetailList.forEach(orderDetail -> {
            Item item = itemService.getItemByItemIdWithLock(orderDetail.getItem().getItemId());
            item.addStock(orderDetail.getAmount());
        });

        User user = userService.getUserByUserId(cancelOrderRequestDto.userId());
        user.chargeBalance(order.getTotalPrice());

        Order cancelOrder = orderService.cancelOrder(cancelOrderRequestDto.orderId(), cancelOrderRequestDto.userId());

        return new CancelOrderResponseDto(cancelOrder.getOrderId(), cancelOrder.getTotalPrice());
    }
}
