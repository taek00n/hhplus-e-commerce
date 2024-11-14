package com.hhplus.ecommerce.infrastructure.event;


public class CreateOrderEvent {

    private final Long orderId;

    public CreateOrderEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
