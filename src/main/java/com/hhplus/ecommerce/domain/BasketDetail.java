package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.BasketErrorCode;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Table(name = "BASKET_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BasketDetail {

    @Id @GeneratedValue
    @Column(name = "BASKET_DETAIL_ID", nullable = false)
    private Long basketDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Column(name = "AMOUNT")
    private int amount;

    public BasketDetail(Basket basket, Item item, int amount) {
        this.basket = basket;
        this.item = item;
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;

        if (this.amount > 10) {
            throw new RestApiException(BasketErrorCode.OVER_ITEM_AMOUNT);
        }
    }
}
