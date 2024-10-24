package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.OrderRepository;
import com.hhplus.ecommerce.infrastructure.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;


    @Override
    public Order save(Order order) {

        Order saveOrder = orderJpaRepository.save(order);

        return saveOrder;
    }

    @Override
    public Optional<Order> findByOrderId(Long ordersId) {

        Optional<Order> getOrder = orderJpaRepository.findById(ordersId);

        return getOrder;
    }

    @Override
    public Optional<Order> findByOrderUser(User user) {

        Optional<Order> getOrder = orderJpaRepository.findByOrderUser(user);

        return getOrder;
    }
}
