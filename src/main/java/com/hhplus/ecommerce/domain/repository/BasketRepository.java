package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Basket;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BasketRepository {

    Optional<Basket> findByBasketId(Long basketId);
    void deleteByBasketId(Long basketId);
    Basket save(Basket basket);
    Basket getUserBasket(@Param("userId") Long userId);
}
