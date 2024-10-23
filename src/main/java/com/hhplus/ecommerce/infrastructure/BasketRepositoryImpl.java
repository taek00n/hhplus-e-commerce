package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.repository.BasketRepository;
import com.hhplus.ecommerce.infrastructure.jpa.BasketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class BasketRepositoryImpl implements BasketRepository {

    private final BasketJpaRepository basketJpaRepository;

    @Override
    public Optional<Basket> findByBasketId(Long basketId) {

        Optional<Basket> getBasket = basketJpaRepository.findByBasketId(basketId);

        return getBasket;
    }

    @Override
    public void deleteByBasketId(Long basketId) {

        basketJpaRepository.deleteByBasketId(basketId);
    }

    @Override
    public Basket save(Basket basket) {
        Basket saveBasket = basketJpaRepository.save(basket);

        return saveBasket;
    }

    @Override
    public Basket getUserBasket(Long userId) {

        Basket getUserBasket = basketJpaRepository.getUserBasket(userId);

        return getUserBasket;
    }
}
