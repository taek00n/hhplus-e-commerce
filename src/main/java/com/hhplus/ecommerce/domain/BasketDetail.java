package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.BasketErrorCode;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(
    name = "BASKET_DETAIL",
    indexes = {
        @Index(name = "idx_basket_detail_basket_id", columnList = "basket_id"),
        @Index(name = "idx_basket_detail_item_id", columnList = "item_id")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BasketDetail {

    @Id @GeneratedValue
    @Column(name = "BASKET_DETAIL_ID", nullable = false)
    private Long basketDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    public BasketDetail(Basket basket, Item item, int amount) {
        this.basket = basket;
        this.item = item;
        this.amount = amount;
        this.createTime = LocalDateTime.now();
    }

    public void addAmount(int amount) {
        this.amount += amount;

        if (this.amount > 10) {
            throw new RestApiException(BasketErrorCode.OVER_ITEM_AMOUNT);
        }
    }
}
