package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemDetail {
    @Id @GeneratedValue
    @Column(name = "ITEM_DETAIL_ID", nullable = false)
    private Long itemDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item itemDetailItem;

    @Column(name = "ITEM_EXPLAIN")
    private String itemExplain;

    @Column(name = "ITEM_PRICE")
    private int itemPrice;

    @Column(name = "ITEM_QUANTITY")
    private int itemQuantity;

    @OneToMany(mappedBy = "ordersDetailItemsDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersDetail> ordersDetails = new ArrayList<>();

    public ItemDetail(Long itemDetailId, Item itemDetailItem, String itemExplain, int itemPrice, int itemQuantity) {
        this.itemDetailId = itemDetailId;
        this.itemDetailItem = itemDetailItem;
        this.itemExplain = itemExplain;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }
}
