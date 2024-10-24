package com.hhplus.ecommerce.infrastructure.jpa;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrder(Order order);
}
