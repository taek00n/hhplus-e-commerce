package com.hhplus.ecommerce.consumer.kafka;

import com.hhplus.ecommerce.common.constant.OutboxStatus;
import com.hhplus.ecommerce.data.DataPlatform;
import com.hhplus.ecommerce.domain.Outbox;
import com.hhplus.ecommerce.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayOrderConsumer {

    private final DataPlatform dataPlatform;
    private final OutboxRepository outboxRepository;

    @KafkaListener(topics = "pay", groupId = "pay1")
    public void pay(Long orderId) {
        log.info(orderId.toString());
        try {
            dataPlatform.savePayOrderData(orderId);
            Outbox outbox = outboxRepository.getOutboxByValueId(orderId);
            outbox.setStatus(OutboxStatus.DONE);
        } catch (Exception e) {
            log.error("PayOrderConsumer" + e.getMessage());
            Outbox outbox = outboxRepository.getOutboxByValueId(orderId);
            outbox.setStatus(OutboxStatus.PENDING);
        }
    }
}
