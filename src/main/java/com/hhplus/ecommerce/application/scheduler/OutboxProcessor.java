package com.hhplus.ecommerce.application.scheduler;

import com.hhplus.ecommerce.infrastructure.kafka.producer.KafkaPayProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final KafkaPayProducer kafkaPayProducer;

    @Scheduled(fixedRate = 5000)
    public void process() {
        kafkaPayProducer.createAgain();
    }
}
