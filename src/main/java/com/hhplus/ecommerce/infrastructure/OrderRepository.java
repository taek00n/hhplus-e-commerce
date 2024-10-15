package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order createOrder(Order order);

    Optional<Order> getOrder(Long ordersId);
}
