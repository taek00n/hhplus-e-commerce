package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.infrastructure.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
