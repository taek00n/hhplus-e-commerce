package com.hhplus.ecommerce.presentation.dto.response.item;

public record ItemResponseDto(
        Long itemId,
        String itemName,
        int itemPrice,
        int itemStock
) {

    public static ItemResponseDto ItemResponseDto(Long itemId, String itemName, int itemPrice, int itemStock) {
        return new ItemResponseDto(itemId, itemName, itemPrice, itemStock);
    }
}
