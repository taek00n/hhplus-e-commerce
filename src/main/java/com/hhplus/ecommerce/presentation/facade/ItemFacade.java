package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemsResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.PopularItemsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemFacade {

    private final ItemService itemService;


    public ItemsResponseDto getItemAll() {

        List<Item> itemAll = itemService.getItemAll();

        return new ItemsResponseDto(itemAll);
    }

    public ItemResponseDto getItemByItemId(Long itemId) {

        Item item = itemService.getItemByItemId(itemId);

        return new ItemResponseDto(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemStock());
    }

    public PopularItemsResponseDto getPopularItem() {

        List<Item> popularItems = itemService.getTopItems();

        return new PopularItemsResponseDto(popularItems);
    }
}
