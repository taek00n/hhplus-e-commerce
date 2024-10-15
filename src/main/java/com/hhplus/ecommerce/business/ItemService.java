package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.infrastructure.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item saveItem(Item item) {

        return itemRepository.save(item);
    }

    public List<Item> getItems() {

        return itemRepository.getItems();
    }

    public Item getItem(long itemId) {

        return itemRepository.getItem(itemId).orElseThrow(
                () -> new IllegalArgumentException("상품이 없습니다.")
        );
    }
}
