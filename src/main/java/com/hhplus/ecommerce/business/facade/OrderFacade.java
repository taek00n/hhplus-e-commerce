package com.hhplus.ecommerce.business.facade;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.business.OrderService;
import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    private final UserService userService;
    private final ItemService itemService;

    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {

        List<OrderDetail> orderDetails = new ArrayList<>();

        User user = userService.getUser(requestDto.userId());
        Order order = new Order(user, OrderStatus.ORDER, LocalDateTime.now());

        requestDto.itemMap().forEach((itemId, amount) -> {
            Item item = itemService.getItem(itemId);
            OrderDetail orderDetail = new OrderDetail(order, item, amount, item.getItemPrice());
            orderDetails.add(orderDetail);
            item.removeStock(amount);
        });

        orderDetails.forEach(orderDetail -> {
            order.addOrderDetail(orderDetail);
        });

        Order createOrder = orderService.createOrder(order);

        return new CreateOrderResponseDto(createOrder.getOrderId(), createOrder.getOrderUser().getUserId(), createOrder.getTotalPrice(), createOrder.getTotalAmount());
    }
}
