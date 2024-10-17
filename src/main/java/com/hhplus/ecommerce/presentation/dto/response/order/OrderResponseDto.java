package com.hhplus.ecommerce.presentation.dto.response.order;

import com.hhplus.ecommerce.domain.OrderDetail;

import java.util.List;

public record OrderResponseDto(
        Long orderId,
        List<OrderDetail> orderDetailList,
        int totalPrice,
        int totalAmount
) {
    public static OrderResponseDto orderResponseDto(Long orderId, List<OrderDetail> orderDetailList, int totalPrice, int totalAmount) {
        return new OrderResponseDto(orderId, orderDetailList, totalPrice, totalAmount);
    }
}
