package com.hhplus.ecommerce.presentation.dto.request.order;

public record CancelOrderRequestDto(
        Long orderId,
        Long userId
) {
    public static CancelOrderRequestDto cancelOrderRequestDto(Long orderId, Long userId) {
        return new CancelOrderRequestDto(orderId, userId);
    }
}
