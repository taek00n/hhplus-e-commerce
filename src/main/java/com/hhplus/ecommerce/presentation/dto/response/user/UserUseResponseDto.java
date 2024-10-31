package com.hhplus.ecommerce.presentation.dto.response.user;

public record UserUseResponseDto(
        Long userId,
        int amount
) {
    public static UserUseResponseDto userUseResponseDto(Long userId, int amount) {
        return new UserUseResponseDto(userId, amount);
    }
}
