package com.hhplus.ecommerce.infrastructure.kafka.producer;

import com.hhplus.ecommerce.domain.Outbox;
import com.hhplus.ecommerce.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaPayProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final OutboxRepository outboxRepository;

    /**
     * 메세지 발행
     * */
    public void create(Long orderId) {
        kafkaTemplate.send("pay", orderId);
    }

    /**
     * 메세지 재발행
     * */
    public void createAgain() {
        List<Outbox> pendingOutbox = outboxRepository.getPendingOutbox();
        try {
            for (Outbox outbox : pendingOutbox) {
                kafkaTemplate.send(outbox.getTopic(), outbox.getValueId());
            }
        } catch (Exception e) {
            log.error("createAgain" + e.getMessage());
        }
    }
}
