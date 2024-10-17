package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDERS_ID", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User orderUser;

    @Column(name = "ORDERS_DATE")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order(User orderUser, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.orderUser = orderUser;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
    }

    public void removeOrdersDetail(OrderDetail orderDetail) {
        this.orderDetails.remove(orderDetail);
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getTotalPrice();
        }

        return totalPrice;
    }

    public int getTotalAmount() {
        int totalAmount = 0;
        for(OrderDetail orderDetail : orderDetails) {
            totalAmount += orderDetail.getAmount();
        }

        return totalAmount;
    }
}
