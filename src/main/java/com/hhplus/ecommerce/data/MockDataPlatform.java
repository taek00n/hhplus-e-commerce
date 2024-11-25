package com.hhplus.ecommerce.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockDataPlatform implements DataPlatform {
    @Override
    public void saveOrderData(Long orderId) {

    }

    @Override
    public void savePayOrderData(Long orderId) {
        log.info("MockDataPlatform savePayOrderData" + orderId);
    }
}
