package com.hhplus.ecommerce.presentation.dto.request.user;

public record UserChargeRequestDto(
        Long userId,
        int balance
) {
    public static UserChargeRequestDto userChargeRequestDto(Long userId, Integer balance) {
        return new UserChargeRequestDto(userId, balance);
    }
}
