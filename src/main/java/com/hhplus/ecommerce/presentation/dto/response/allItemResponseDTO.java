package com.hhplus.ecommerce.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class allItemResponseDTO {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int itemQuantity;
}
