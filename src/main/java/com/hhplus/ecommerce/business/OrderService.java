package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Item> findTopItems() {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        return orderRepository.findTopItems(startDateTime, endDateTime);
    }
}
