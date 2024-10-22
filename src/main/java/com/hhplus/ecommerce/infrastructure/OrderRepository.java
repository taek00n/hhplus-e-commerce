package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order save(Order order);

    Optional<Order> findByOrderId(Long ordersId);
    Optional<Order> findByOrderUser(User user);
}
