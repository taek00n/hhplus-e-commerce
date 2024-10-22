package com.hhplus.ecommerce.presentation.dto.response.item;

import com.hhplus.ecommerce.domain.Item;

import java.util.List;

public record PopularItemsResponseDto(
        List<Item> items
) {
    public static PopularItemsResponseDto popularItemsResponseDto(List<Item> items) {
        return  new PopularItemsResponseDto(items);
    }
}
