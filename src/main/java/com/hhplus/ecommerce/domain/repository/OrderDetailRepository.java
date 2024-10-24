package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {

    List<OrderDetail> findByOrder(Order order);
}
