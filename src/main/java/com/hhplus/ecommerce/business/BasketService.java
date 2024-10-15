package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.infrastructure.BasketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public Basket createBasket(Basket basket) {

        return basketRepository.createBasket(basket);
    }

    public void removeBasket(long basketId) {

        Basket basket = this.getBasket(basketId);

        basketRepository.removeBasket(basket);
    }

    public Basket getBasket(Long basketId) {

        Basket basket = basketRepository.getBasket(basketId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        return basket;
    }

    public Basket getUserBasket(long userId) {

        Basket userBasket = basketRepository.getUserBasket(userId);

        return userBasket;
    }
}
