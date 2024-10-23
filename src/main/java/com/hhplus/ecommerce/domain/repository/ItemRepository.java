package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item item);
    Optional<Item> findByItemId(Long itemId);
    List<Item> findAll();
    List<Item> findTopItems(LocalDateTime startDateTime, LocalDateTime endDateTime);
    Item findByItemIdWithLock(Long itemId);
}
