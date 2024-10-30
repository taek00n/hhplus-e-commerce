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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
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
        int remainStock = this.itemStock - amount;
        if (remainStock < 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
            throw new RestApiException(ItemErrorCode.ITEM_SOLD_OUT);
        }
        this.itemStock = remainStock;
    }

    public void addStock(int amount) {
        this.itemStock += amount;
    }
}
