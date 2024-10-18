package com.hhplus.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BASKET_DT")
public class BasketDt {

    @Id
    private Long basketDtId;
}
