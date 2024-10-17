package com.hhplus.ecommerce.presentation.dto.response.basket;

public record DeleteBasketDetailResponseDto(
        Long basketDetailId,
        Long itemId,
        String itemName
) {
    public static DeleteBasketDetailResponseDto deleteBasketDetailResponseDto(Long basketDetailId, Long itemId, String itemName) {
        return new DeleteBasketDetailResponseDto(basketDetailId, itemId, itemName);
    }
}
