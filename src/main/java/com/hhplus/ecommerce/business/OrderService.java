package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {

        return orderRepository.createOrder(order);
    }

    public Order getOrder(Long ordersId) {

        return orderRepository.getOrder(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문은 존재하지않습니다."));
    }
}
