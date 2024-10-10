package com.hhplus.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDER")
public class Order {
    @Id
    private Long orderId;
}
