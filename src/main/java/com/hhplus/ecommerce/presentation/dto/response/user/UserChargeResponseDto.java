package com.hhplus.ecommerce.presentation.dto.response.user;

import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;

public record UserChargeResponseDto(
        Long userId,
        int balance,
        int totalBalance
) {
    public static UserChargeResponseDto userChargeResponseDto(Long userId, int balance, int totalBalance) {
        return new UserChargeResponseDto(userId, balance, totalBalance);
    }
}
