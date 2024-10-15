package com.hhplus.ecommerce.presentation.dto.response.order;

public record CreateOrderResponseDto(
        Long orderId,
        Long userId,
        int totalPrice,
        int totalAmount
) {
    public static CreateOrderResponseDto CreateOrderFacadeResponseDto(Long orderId, Long userId, Integer totalPrice, Integer totalAmount) {
        return new CreateOrderResponseDto(orderId, userId, totalPrice, totalAmount);
    }
}
