package com.hhplus.ecommerce.infrastructure.jpa;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Order save(Order order);
    Optional<Order> findByOrderId(Long ordersId);
    Optional<Order> findByOrderUser(User user);
}
