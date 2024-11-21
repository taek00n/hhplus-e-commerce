package com.hhplus.ecommerce.data;

public interface DataPlatform {

    void saveOrderData(Long orderId);
    void savePayOrderData(Long orderId);
}
