package com.hhplus.ecommerce.presentation.dto.request.basket;

import com.hhplus.ecommerce.domain.Basket;

public record BasketRequestDto(
        Long userId
) {
    public static BasketRequestDto basketRequestDto(Long userId) {
        return new BasketRequestDto(userId);
    }
}
