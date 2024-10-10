package com.hhplus.ecommerce.business.repository;

import com.hhplus.ecommerce.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
