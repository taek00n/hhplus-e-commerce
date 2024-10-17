package com.hhplus.ecommerce.presentation.dto.request.order;

public record OrderRequestDto(
        Long userId
) {
    public static OrderRequestDto orderRequestDto(Long userId) {
        return new OrderRequestDto(userId);
    }
}
