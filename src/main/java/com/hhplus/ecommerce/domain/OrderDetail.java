package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "ORDERS_DETAIL",
    indexes = {
        @Index(name = "idx_orders_detail_order_id", columnList = "order_id"),
        @Index(name = "idx_orders_detail_item_id", columnList = "item_id")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail {

    @Id @GeneratedValue
    @Column(name = "ORDERS_DETAIL_ID", nullable = false)
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Column(name = "ORDER_PRICE")
    private int orderPrice;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "ORDERS_DATE")
    private LocalDateTime orderDate;

    public OrderDetail(Order order, Item item, int amount, int orderPrice) {
        this.order = order;
        this.item = item;
        this.amount = amount;
        this.orderPrice = orderPrice;
        this.orderDate = LocalDateTime.now();
    }

    public int getTotalPrice() {

        return this.orderPrice * this.amount;
    }
}
