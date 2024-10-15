package com.hhplus.ecommerce.presentation.dto.request.order;

import java.util.List;
import java.util.Map;

public record CreateOrderRequestDto(
    Long userId,
    Map<Long, Integer> itemMap,
    int amount
) {
    public static CreateOrderRequestDto createOrderRequestDto(Long userId, Map<Long, Integer> itemMap, int amount) {
        return new CreateOrderRequestDto(userId, itemMap, amount);
    }
}
