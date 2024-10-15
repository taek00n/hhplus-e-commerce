package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail {

    @Id @GeneratedValue
    @Column(name = "ORDERS_DETAIL_ID", nullable = false)
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(name = "ORDER_PRICE")
    private int orderPrice  ;

    @Column(name = "AMOUNT")
    private int amount;

    public OrderDetail(Order order, Item item, int amount, int orderPrice) {
        this.order = order;
        this.item = item;
        this.amount = amount;
        this.orderPrice = orderPrice;
    }

    public int getTotalPrice() {

        return orderPrice * amount;
    }
}
