package com.hhplus.ecommerce.infrastructure.event;

import com.hhplus.ecommerce.data.DataPlatform;
import com.hhplus.ecommerce.domain.Outbox;
import com.hhplus.ecommerce.domain.repository.OutboxRepository;
import com.hhplus.ecommerce.infrastructure.kafka.producer.KafkaPayProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderEventListener {

    private final OutboxRepository outboxRepository;
    private final KafkaPayProducer kafkaPayProducer;

    @TransactionalEventListener(
            value = PayOrderEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void saveOutbox(PayOrderEvent payOrderEvent) {
        Outbox outbox = new Outbox("pay", payOrderEvent.orderId());
        outboxRepository.save(outbox);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePayOrderEvent(PayOrderEvent payOrderEvent) {

        try {
            log.info("createOrderEvent => " + payOrderEvent.orderId());
            // kafka 호출
            kafkaPayProducer.create(payOrderEvent.orderId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
