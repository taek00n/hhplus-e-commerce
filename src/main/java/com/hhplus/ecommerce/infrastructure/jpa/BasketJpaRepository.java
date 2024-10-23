package com.hhplus.ecommerce.infrastructure.jpa;

import com.hhplus.ecommerce.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BasketJpaRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByBasketId(Long basketId);
    void deleteByBasketId(Long basketId);
    Basket save(Basket basket);

    @Query(value = "select b from Basket b where b.basketUser.userId = :userId")
    Basket getUserBasket(@Param("userId") Long userId);
}
