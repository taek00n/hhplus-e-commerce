package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;

import java.util.List;
import java.util.Optional;

public interface BasketDetailRepository {

    BasketDetail save(BasketDetail basketDetail);
    BasketDetail findByItem(Item item);
    List<BasketDetail> findAllByBasket(Basket basket);
    Optional<BasketDetail> findById(Long basketDetailId);
    void delete(BasketDetail basketDetail);
}
