package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.ItemSellStatus;
import com.hhplus.ecommerce.common.exception.OutOfStockException;
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

    @OneToMany(mappedBy = "basketDetailItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketDetail> basketDetails = new ArrayList<>();

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

    public void removeStock(int amount) {
        int restStock = this.itemStock - amount;
        if (restStock < 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 : " + this.itemStock + ")");
        }
        this.itemStock = restStock;
    }
}
