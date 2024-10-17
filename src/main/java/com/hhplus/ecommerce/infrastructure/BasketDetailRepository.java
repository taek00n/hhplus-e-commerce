package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketDetailRepository extends JpaRepository<BasketDetail, Long> {

    @Query(value = "select b from BasketDetail b where b.item = :item")
    BasketDetail findBasketDetailByItem(@Param("item")Item item);

    @Query(value = "select b from BasketDetail b where b.basket = :basket")
    List<BasketDetail> findAllByBasket(@Param("basket") Basket basket);
}
