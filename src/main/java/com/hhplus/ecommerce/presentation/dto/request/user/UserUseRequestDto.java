package com.hhplus.ecommerce.presentation.dto.request.user;

public record UserUseRequestDto(
        Long userId,
        int amount
) {
    public static UserUseRequestDto userUseRequestDto(Long userId, Integer amount) {
        return new UserUseRequestDto(userId, amount);
    }
}
