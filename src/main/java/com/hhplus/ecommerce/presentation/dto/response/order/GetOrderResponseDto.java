package com.hhplus.ecommerce.presentation.dto.response.order;

import com.hhplus.ecommerce.domain.OrderDetail;

import java.util.List;

public record GetOrderResponseDto(
        Long orderId,
        List<OrderDetail> orderDetailList,
        int totalPrice,
        int totalAmount,
        String payYn
) {
    public static GetOrderResponseDto getOrderFacadeResponseDto(Long orderId, List<OrderDetail> orderDetailList, int totalPrice, int totalAmount, String payYn) {
        return new GetOrderResponseDto(orderId, orderDetailList, totalPrice, totalAmount, payYn);
    }
}
