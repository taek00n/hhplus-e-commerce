package com.hhplus.ecommerce.presentation.dto.request.pay;

public record PayRequestDto(
        Long orderId
) {
    public static PayRequestDto payFacadeRequestDto(Long orderId) {
        return new PayRequestDto(orderId);
    }
}
