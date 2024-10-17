package com.hhplus.ecommerce.presentation.dto.request.user;

public record UserBalanceRequestDto(
        Long userId
) {
    public static UserBalanceRequestDto userBalanceRequestDto(Long userId) {
        return new UserBalanceRequestDto(userId);
    }
}
