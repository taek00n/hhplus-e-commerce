package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemsResponseDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/items")
@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ItemsResponseDto getItems() {

        return new ItemsResponseDto(itemService.getItems());
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto getItem(@PathVariable Long itemId) {

        Item item = itemService.getItem(itemId);
        return new ItemResponseDto(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemStock());
    }
}
