package com.hhplus.ecommerce.infrastructure.event;

import com.hhplus.ecommerce.data.DataPlatform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderEventListener {

    private final DataPlatform dataPlatform;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreateOrderEvent(CreateOrderEvent createOrderEvent) {
        dataPlatform.saveOrderData(createOrderEvent.getOrderId());
        log.info("createOrderEvent => " + createOrderEvent.getOrderId());
    }
}
