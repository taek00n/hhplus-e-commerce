package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Orders;
import com.hhplus.ecommerce.infrastructure.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Orders save(Orders orders) {

        return ordersRepository.save(orders);
    }
}
