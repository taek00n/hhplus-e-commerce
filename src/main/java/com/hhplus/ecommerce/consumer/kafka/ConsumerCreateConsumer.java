package com.hhplus.ecommerce.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerCreateConsumer {

    @KafkaListener(topics = "kafka-test", groupId = "test")
    public void listener(Long userId) {
        log.info(userId.toString());
    }
}
