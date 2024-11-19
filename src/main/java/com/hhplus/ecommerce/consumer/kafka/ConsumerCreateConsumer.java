package com.hhplus.ecommerce.consumer.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerCreateConsumer {

    @KafkaListener(topics = "kafka-test", groupId = "test")
    public void listener(Long userId) {

    }
}
