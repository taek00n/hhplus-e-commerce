package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(name = "AMOUNT")
    private int amount;

    public BasketDetail(Basket basket, Item item, int amount) {
        this.basket = basket;
        this.item = item;
        this.amount = amount;
    }
}
