package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.ItemSellStatus;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID", nullable = false)
    private Long itemId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_PRICE")
    private int itemPrice;

    @Column(name = "ITEM_STOCK")
    private int itemStock;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Column(name = "REGISTER_DATE")
    private LocalDateTime registerDate;

    public Item(String itemName, int itemPrice, int itemStock, LocalDateTime registerDate) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.itemSellStatus = ItemSellStatus.SELL;
        this.registerDate = registerDate;

        if(itemStock < 1) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
    }

    public void reduceStock(int amount) {
        if (this.itemStock < amount) {
            throw new RestApiException(ItemErrorCode.NO_ENOUGH_ITEM);
        }
        this.itemStock -= amount;
    }

    public void addStock(int amount) {
        this.itemStock += amount;

        if (this.itemStock > 0) {
            this.itemSellStatus = ItemSellStatus.SELL;
        }
    }
}
