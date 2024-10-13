package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "REGISTER_DATE")
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "basketDetailItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketDetail> basketDetails = new ArrayList<>();

    @OneToMany(mappedBy = "itemDetailItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemDetail> itemDetails = new ArrayList<>();

    public Item(Long itemId, String itemName, LocalDateTime registerDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.registerDate = registerDate;
    }

    public void addBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.add(basketDetail);
    }

    public void removeBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.remove(basketDetail);
    }

    public void addItemDetail(ItemDetail itemDetail) {
        this.itemDetails.add(itemDetail);
    }

    public void removeItemDetail(ItemDetail itemDetail) {
        this.itemDetails.remove(itemDetail);
    }
}
