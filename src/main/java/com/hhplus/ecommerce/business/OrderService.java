package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.OrderErrorCode;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
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

    public List<Item> findTopItems() {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        return orderRepository.findTopItems(startDateTime, endDateTime);
    }
}
