package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.infrastructure.OrdersDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersDetailService {

    private final OrdersDetailRepository ordersDetailRepository;
}
