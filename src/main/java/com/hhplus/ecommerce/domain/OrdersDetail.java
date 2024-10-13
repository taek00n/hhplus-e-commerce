package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrdersDetail {

    @Id @GeneratedValue
    @Column(name = "ORDERS_DETAIL_ID", nullable = false)
    private Long ordersDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders ordersDetailOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_DETAIL_ID")
    private ItemDetail ordersDetailItemsDetail;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_PRICE")
    private int itemPrice;

    @Column(name = "AMOUNT")
    private int amount;

    public OrdersDetail(Long ordersDetailId, Orders ordersDetailOrders, ItemDetail ordersDetailItemsDetail, String itemName, int itemPrice, int amount) {
        this.ordersDetailId = ordersDetailId;
        this.ordersDetailOrders = ordersDetailOrders;
        this.ordersDetailItemsDetail = ordersDetailItemsDetail;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amount = amount;
    }
}
