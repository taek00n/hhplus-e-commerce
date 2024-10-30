package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.OrderErrorCode;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {

        return orderRepository.save(order);
    }

    public Order getOrderByOrderId(Long orderId) {

        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.NO_ORDER_BY_ID));
    }

    public Order getOrderByUser(User user) {

        return orderRepository.findByOrderUser(user)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.NO_ORDER_BY_ID));
    }

    @Transactional
    public Order cancelOrder(Long orderId, Long userId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.NO_ORDER_BY_ID));

        if (order.getOrderUser().getUserId() != userId) {
            throw new RestApiException(OrderErrorCode.NOT_EQUALS_USER);
        }

        if (!order.getOrderStatus().equals(OrderStatus.ORDER)) {
            throw new RestApiException(OrderErrorCode.NOT_EQUALS_STATUS);
        }

        order.changeOrderStatus(OrderStatus.CANCEL);

        return order;
    }
}
