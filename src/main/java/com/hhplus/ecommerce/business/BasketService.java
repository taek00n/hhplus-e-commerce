package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.BasketErrorCode;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.repository.BasketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public Basket createBasket(Basket basket) {

        return basketRepository.save(basket);
    }

    public void removeBasketByBasketId(Long basketId) {

        basketRepository.deleteByBasketId(basketId);
    }

    public Basket getBasket(Long basketId) {

        Basket basket = basketRepository.findByBasketId(basketId)
                .orElseThrow(() -> new RestApiException(BasketErrorCode.NO_BASKET_BY_ID));

        return basket;
    }

    public Basket getUserBasket(Long userId) {

        Basket userBasket = basketRepository.getUserBasket(userId);

        return userBasket;
    }
}
