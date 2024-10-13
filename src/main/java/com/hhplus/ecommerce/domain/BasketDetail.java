package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BASKET_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BasketDetail {

    @Id @GeneratedValue
    @Column(name = "BASKET_DETAIL_ID", nullable = false)
    private Long basketDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID")
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private ItemDetail basketDetailItem;

    @Column(name = "AMOUNT")
    private int amount;

    public BasketDetail(Long basketDetailId, Basket basket, ItemDetail basketDetailItem, int amount) {
        this.basketDetailId = basketDetailId;
        this.basket = basket;
        this.basketDetailItem = basketDetailItem;
        this.amount = amount;
    }
}
