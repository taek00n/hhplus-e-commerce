package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.repository.OrderDetailRepository;
import com.hhplus.ecommerce.infrastructure.jpa.OrderDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Repository
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    private final OrderDetailJpaRepository orderDetailJpaRepository;

    @Override
    public List<OrderDetail> findByOrder(Order order) {
        List<OrderDetail> getOrderDetailList = orderDetailJpaRepository.findByOrder(order);

        return getOrderDetailList;
    }
}
