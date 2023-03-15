package com.test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerMessage {
    @KafkaListener(topics = "test-kafka", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received Message in group - group-id: " + message);
    }
}
