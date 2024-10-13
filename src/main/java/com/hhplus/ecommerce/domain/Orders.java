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
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Orders {

    @Id @GeneratedValue
    @Column(name = "ORDERS_ID", nullable = false)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UESR_ID")
    private User orderUser;

    @Column(name = "ORDERS_DATE")
    private LocalDateTime ordersDate;

    @Column(name = "PAY_YN")
    private String payYn;

    @OneToMany(mappedBy = "ordersDetailOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersDetail> ordersDetails = new ArrayList<>();

    public Orders(Long ordersId, User orderUser, LocalDateTime ordersDate) {
        this.ordersId = ordersId;
        this.orderUser = orderUser;
        this.ordersDate = ordersDate;
        this.payYn = "N";
    }

    public void addOrdersDetail(OrdersDetail ordersDetail) {
        this.ordersDetails.add(ordersDetail);
    }

    public void removeOrdersDetail(OrdersDetail ordersDetail) {
        this.ordersDetails.remove(ordersDetail);
    }
}
