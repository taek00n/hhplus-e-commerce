package com.hhplus.ecommerce.presentation.dto.response.order;

public record CancelOrderResponseDto(
        Long orderId,
        int totalPrice
) {
    public static CancelOrderResponseDto cancelOrderResponseDto(Long orderId, int totalPrice) {
        return new CancelOrderResponseDto(orderId, totalPrice);
    }
}
