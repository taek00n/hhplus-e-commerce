package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.OrdersDetail;
import com.hhplus.ecommerce.infrastructure.OrdersDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersDetailService {

    private final OrdersDetailRepository ordersDetailRepository;

    public OrdersDetail save(OrdersDetail ordersDetail) {

        return ordersDetailRepository.save(ordersDetail);
    }

    public OrdersDetail getOrdersDetail(Long ordersDetailId) {

        OrdersDetail ordersDetail = ordersDetailRepository.findById(ordersDetailId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 상세입니다."));

        return ordersDetail;
    }
}
