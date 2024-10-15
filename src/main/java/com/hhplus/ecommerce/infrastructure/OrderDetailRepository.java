package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail createOrderDetail(OrderDetail orderDetail);

    Optional<OrderDetail> getOrderDetail(Long ordersDetailId);

    List<OrderDetail> getOrderDetails(Long orderId);
}
