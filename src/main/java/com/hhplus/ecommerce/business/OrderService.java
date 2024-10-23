package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.OrderErrorCode;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
}
