package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import com.hhplus.ecommerce.infrastructure.jpa.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;


    @Override
    public Item save(Item item) {
        Item saveItem = itemJpaRepository.save(item);

        return saveItem;
    }

    @Override
    public Optional<Item> findByItemId(Long itemId) {

        Optional<Item> getItem = itemJpaRepository.findByItemId(itemId);

        return getItem;
    }

    @Override
    public List<Item> findAll() {

        List<Item> getItemList = itemJpaRepository.findAll();

        return getItemList;
    }

    @Override
    public List<Item> findTopItems(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        List<Item> getTopItemList = itemJpaRepository.findTopItems(startDateTime, endDateTime);

        return getTopItemList;
    }

    @Override
    public Item findByItemIdWithLock(Long itemId) {

        Item getLockItem = itemJpaRepository.findByItemIdWithLock(itemId);

        return getLockItem;
    }
}
