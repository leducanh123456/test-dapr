package com.test.kafka;

//@Service
//@Slf4j
public class ConsumerMessage {
    //@KafkaListener(topics = "test-kafka", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received Message in group - group-id: " + message);
    }
}
