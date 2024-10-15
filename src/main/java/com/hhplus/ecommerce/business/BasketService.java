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

    public Basket save(Basket basket) {

        return basketRepository.save(basket);
    }

    public void delete(long basketId) {

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 할려는 장바구니가 없습니다."));

        basketRepository.delete(basket);
    }

    public Basket getBasket(Long basketId) {

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        return basket;
    }
}
