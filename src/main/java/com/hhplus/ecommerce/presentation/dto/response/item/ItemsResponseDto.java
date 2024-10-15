package com.hhplus.ecommerce.presentation.dto.response.item;

import com.hhplus.ecommerce.domain.Item;

import java.util.List;

public record ItemsResponseDto(
        List<Item> items
) {
    public static ItemsResponseDto itemsResponse(List<Item> items) {
        return new ItemsResponseDto(items);
    }
}
