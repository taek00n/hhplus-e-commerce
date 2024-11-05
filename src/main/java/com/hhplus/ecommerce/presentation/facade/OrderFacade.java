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
import java.util.Map;

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
        int totalPrice = orderDetailList.stream().mapToInt(OrderDetail::getTotalPrice).sum();
        int totalAmount = orderDetailList.stream().mapToInt(OrderDetail::getAmount).sum();

        return new OrderResponseDto(order.getOrderId(), orderDetailList, totalPrice, totalAmount);
    }

    @Transactional
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {

        int totalPrice = 0;
        int totalAmount = 0;

        User user = userService.getUserByUserId(requestDto.userId());
        Order createOrder = orderService.createOrder(new Order(user, OrderStatus.ORDER, LocalDateTime.now()));

        for(Long itemId : requestDto.itemMap().keySet()) {
            Item item = itemService.getItemByItemIdWithLock(itemId);
            int price = item.getItemPrice();
            int amount = requestDto.itemMap().get(itemId);
            OrderDetail orderDetail = new OrderDetail(createOrder, item, amount, price);
            orderDetailService.createOrderDetail(orderDetail);
            itemService.reduceItemStockWithRedisson(item, amount);
            totalPrice += price;
            totalAmount += amount;
        }


        return new CreateOrderResponseDto(createOrder.getOrderId(), createOrder.getOrderUser().getUserId(), totalPrice, totalAmount);
    }

    @Transactional
    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {

        Order order = orderService.getOrderByOrderId(cancelOrderRequestDto.orderId());

        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(order);
        int totalPrice = orderDetailList.stream().mapToInt(OrderDetail::getOrderPrice).sum();
        orderDetailList.forEach(orderDetail -> {
            Item item = itemService.getItemByItemIdWithLock(orderDetail.getItem().getItemId());
            item.addStock(orderDetail.getAmount());
        });

        User user = userService.getUserByUserId(cancelOrderRequestDto.userId());
        user.chargeBalance(totalPrice);

        Order cancelOrder = orderService.cancelOrder(cancelOrderRequestDto.orderId(), cancelOrderRequestDto.userId());

        return new CancelOrderResponseDto(cancelOrder.getOrderId(), totalPrice);
    }
}
