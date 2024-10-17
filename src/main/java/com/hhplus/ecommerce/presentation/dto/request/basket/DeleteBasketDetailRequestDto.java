package com.hhplus.ecommerce.presentation.dto.request.basket;

import java.util.Map;

public record DeleteBasketDetailRequestDto(
        Long userId,
        Long itemId
) {
    public static DeleteBasketDetailRequestDto deleteBasketDetailRequestDto(Long userId, Long itemId) {
        return new DeleteBasketDetailRequestDto(userId, itemId);
    }
}
