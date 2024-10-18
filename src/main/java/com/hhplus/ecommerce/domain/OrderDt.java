package com.hhplus.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "ORDER_DT")
@Entity
public class OrderDt {
    @Id
    private Long orderDtId;
}
