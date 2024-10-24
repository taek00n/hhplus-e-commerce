package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.BasketDetailRepository;
import com.hhplus.ecommerce.infrastructure.jpa.BasketDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class BasketDetailRepositoryImpl implements BasketDetailRepository {

    private final BasketDetailJpaRepository basketDetailJpaRepository;

    @Override
    public BasketDetail save(BasketDetail basketDetail) {
        BasketDetail saveBasketDetail = basketDetailJpaRepository.save(basketDetail);

        return saveBasketDetail;
    }

    @Override
    public BasketDetail findByItem(Item item) {

        BasketDetail getBasketDetailList = basketDetailJpaRepository.findByItem(item);

        return getBasketDetailList;
    }

    @Override
    public List<BasketDetail> findAllByBasket(Basket basket) {

        List<BasketDetail> getBaketDetailList = basketDetailJpaRepository.findAllByBasket(basket);

        return getBaketDetailList;
    }

    @Override
    public Optional<BasketDetail> findById(Long basketDetailId) {
        Optional<BasketDetail> getBasketDetail = basketDetailJpaRepository.findById(basketDetailId);

        return getBasketDetail;
    }

    @Override
    public void delete(BasketDetail basketDetail) {

        basketDetailJpaRepository.delete(basketDetail);
    }
}
