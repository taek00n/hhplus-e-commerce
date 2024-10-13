package com.hhplus.ecommerce.business.impl;

import com.hhplus.ecommerce.business.OrdersService;
import com.hhplus.ecommerce.infrastructure.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
}
