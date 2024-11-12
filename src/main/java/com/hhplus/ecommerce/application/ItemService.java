package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.constant.ItemSellStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final RedissonClient redissonClient;

    public Item createItem(Item item) {

        return itemRepository.save(item);
    }

    @Cacheable(cacheNames = "allItem", key = "'allItems'", unless = "#result == null || #result.isEmpty()")
    public List<Item> getItemAll() {

        return itemRepository.findAll();
    }

    public Item getItemByItemId(long itemId) {

        return itemRepository.findByItemId(itemId).orElseThrow(
                () -> new RestApiException(ItemErrorCode.NO_ITEM_BY_ID)
        );
    }

    public Item getItemByItemIdWithLock(long ItemId) {

        Item byItemIdWithLock = itemRepository.findByItemIdWithLock(ItemId);

        return byItemIdWithLock;
    }

    @Cacheable(cacheNames = "topItems", key = "'topItems'")
    public List<Item> getTopItems() {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        List<Item> geTopItemList = itemRepository.findTopItems(startDateTime, endDateTime);
        return geTopItemList;
    }

    @CachePut(cacheNames = "topItems", key = "'topItems'")
    @Scheduled(cron = "0 0 12 * * ?")
    public List<Item> refreshGetTopItems() {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        List<Item> geTopItemList = itemRepository.findTopItems(startDateTime, endDateTime);
        return geTopItemList;
    }

    public void reduceItemStockWithRedisson(Item item, int amount) {

        RLock lock = redissonClient.getLock("itemId:" + item.getItemId());

        try {
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

            if (!available) {
                return;
            }

            Item getItem = itemRepository.findByItemId(item.getItemId()).orElseThrow(
                    () -> new RestApiException(ItemErrorCode.NO_ITEM_BY_ID)
            );

            if (getItem.getItemSellStatus().equals(ItemSellStatus.SOLD_OUT)) {
                throw new RestApiException(ItemErrorCode.ITEM_SOLD_OUT);
            }

            getItem.reduceStock(amount);
            itemRepository.save(getItem);

        } catch (InterruptedException e) {
            throw new RuntimeException("failed");
        } finally {
            if(lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
