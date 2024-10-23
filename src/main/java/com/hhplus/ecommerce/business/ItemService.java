package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.constant.ItemSellStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem(Item item) {

        return itemRepository.save(item);
    }

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

    public List<Item> getTopItems() {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        return itemRepository.findTopItems(startDateTime, endDateTime);
    }
}
