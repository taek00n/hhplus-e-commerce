package com.hhplus.ecommerce.presentation.dto.response.basket;

import com.hhplus.ecommerce.domain.Basket;

public record AddBasketResponseDto(
    Long basketId
) {
    public static AddBasketResponseDto addBasketResponseDto(Basket basket) {
        return new AddBasketResponseDto(basket.getBasketId());
    }
}
