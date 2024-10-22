package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemId(Long itemId);
    List<Item> findAll();

    @Query(value = "select i from Item i left join OrderDetail od on i.itemId = od.item.itemId left join Order o on od.order.orderId = o.orderId where o.orderDate between :startDateTime and :endDateTime group by i order by od.amount desc limit 5")
    List<Item> findTopItems(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
