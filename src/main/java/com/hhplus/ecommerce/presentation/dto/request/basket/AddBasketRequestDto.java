package com.hhplus.ecommerce.presentation.dto.request.basket;

import java.util.Map;

public record AddBasketRequestDto(
        Long userId,
        Map<Long, Integer> itemMap
) {
    public static AddBasketRequestDto addBasketRequestDto(Long userId, Map<Long, Integer> itemMap) {
        return new AddBasketRequestDto(userId, itemMap);
    }
}
