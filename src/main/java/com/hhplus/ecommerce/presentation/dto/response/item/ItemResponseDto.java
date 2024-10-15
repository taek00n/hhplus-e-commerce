package com.hhplus.ecommerce.presentation.dto.response.item;

public record ItemResponseDto(
        Long itemId,
        String itemName,
        int itemPrice,
        int itemQuantity
) {

    public static ItemResponseDto ItemResponseDto(Long itemId, String itemName, int itemPrice, int itemQuantity) {
        return new ItemResponseDto(itemId, itemName, itemPrice, itemQuantity);
    }
}
