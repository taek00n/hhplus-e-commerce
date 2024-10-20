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

    @Query(value = "select d.item, count(*) from Order o left join o.orderDetails d where o.orderDate between :startDateTime and :endDateTime group by d.item order by count(*) desc")
    List<Item> findTopItems(LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<Order> findByOrderUser(User user);
}
