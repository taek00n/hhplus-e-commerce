package com.hhplus.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "ITEM_DT")
@Entity
public class ItemDt {
    @Id
    private Long itemDtId;
}
