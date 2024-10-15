package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
}
