package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
