package com.hhplus.ecommerce.presentation.dto.response.basket;

import com.hhplus.ecommerce.domain.BasketDetail;

import java.util.List;

public record BasketResponseDto(
        Long basketId,
        List<BasketDetail> detailList
) {
    public static BasketResponseDto basketDetailResponseDto(Long basketId, List<BasketDetail> detailList) {
        return new BasketResponseDto(basketId, detailList);
    }
}
