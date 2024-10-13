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

    @OneToMany(mappedBy = "ordersDetailOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersDetail> ordersDetails = new ArrayList<>();
}
