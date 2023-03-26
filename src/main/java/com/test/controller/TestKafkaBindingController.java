package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kafka.ProducerMessage;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class TestKafkaBindingController {

    private static final Logger logger = LoggerFactory.getLogger(TestKafkaBindingController.class);
    @Autowired
    private ProducerMessage producerMessage;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/send-message")
    public String sendSMSToKafka() throws Exception {
        System.out.println("----------------START SEND-----------");
        String mes = "Sending message to kafka topic by cafeincode:" + new Random().nextInt(100);
        //producerMessage.sendMessage(mes);
        sendMessageBinding(mes);
        System.out.println("----------------END SEND-------------");
        return "OK";
    }

    public void sendMessage(String message) throws Exception {
        String TOPIC_NAME = "test-kafka";
        String PUBSUB_NAME = "orderpubsub";
        DaprClient client = new DaprClientBuilder().build();

        for (int i = 0; i <= 10; i++) {
            int orderId = i;
            Order order = new Order(orderId);
            logger.info(objectMapper.writeValueAsString(order));
            // Publish an event/message using Dapr PubSub
            client.publishEvent(PUBSUB_NAME, TOPIC_NAME, objectMapper.writeValueAsBytes(order)).block();
            TimeUnit.MILLISECONDS.sleep(100);

        }
    }

    public void sendMessageBinding(String message) throws Exception {
        String BINDING_NAME = "checkout";
        String BINDING_OPERATION = "create";
        int k =0;
        DaprClient client = new DaprClientBuilder().build();
        while(k< 3) {

            Random random = new Random();
            int orderId = random.nextInt(1000-1) + 1;
            Order order = new Order(orderId);
            //Using Dapr SDK to invoke output binding
            logger.info(objectMapper.writeValueAsString(objectMapper.writeValueAsString(order)));
            client.invokeBinding(BINDING_NAME, BINDING_OPERATION, objectMapper.writeValueAsString(order)).block();
            k++;
        }
    }

    @AllArgsConstructor
    @Getter
    class Order {
        private int orderId;
    }
}
