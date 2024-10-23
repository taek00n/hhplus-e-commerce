package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findByOrderId(Long ordersId);
    Optional<Order> findByOrderUser(User user);
}
