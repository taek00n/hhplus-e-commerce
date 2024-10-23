package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.BasketErrorCode;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.BasketDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BasketDetailService {

    private final BasketDetailRepository basketDetailRepository;

    public BasketDetail createBasketDetail(BasketDetail basketDetail) {

        return basketDetailRepository.save(basketDetail);
    }

    public void delete(Long basketDetailId) {

        BasketDetail basketDetail = basketDetailRepository.findById(basketDetailId)
                .orElseThrow(() -> new RestApiException(BasketErrorCode.NO_BASKET_DETAIL_BY_ID));
        basketDetailRepository.delete(basketDetail);
    }

    public BasketDetail getBasketDetail(Long basketDetailId) {

        BasketDetail basketDetail = basketDetailRepository.findById(basketDetailId)
                .orElseThrow(() -> new RestApiException(BasketErrorCode.NO_BASKET_DETAIL_BY_ID));

        return basketDetail;
    }

    public BasketDetail getBasketDetailByItem(Item item) {

        BasketDetail basketDetail = basketDetailRepository.findByItem(item);

        return basketDetail;
    }

    public List<BasketDetail> getAllDetailByBasket(Basket basket) {

        List<BasketDetail> allDetailByBasket = basketDetailRepository.findAllByBasket(basket);

        if (allDetailByBasket.isEmpty()) {
            throw new RestApiException(BasketErrorCode.NO_BASKET_DETAIL_BY_ID);
        }

        return allDetailByBasket;
    }
}
