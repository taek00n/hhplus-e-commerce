package com.hhplus.ecommerce.presentation.dto.response.user;

public record UserBalanceResponseDto(
        Long userId,
        int balance
) {
    public static UserBalanceResponseDto userBalanceResponseDto(Long userId, Integer balance) {
        return new UserBalanceResponseDto(userId, balance);
    }
}
