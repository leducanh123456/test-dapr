package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.Order;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class TestKafkaPubSubController {

   // private static final Logger logger = LoggerFactory.getLogger(TestKafkaBindingController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/send-pubsub")
    public String sendSMSToKafka() throws Exception {
        System.out.println("----------------START SEND-----------");
        String mes = "Sending message to kafka topic by cafeincode:" + new Random().nextInt(100);
        //producerMessage.sendMessage(mes);
        sendMessage(mes);
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
            //logger.info(objectMapper.writeValueAsString(order));
            // Publish an event/message using Dapr PubSub
            client.publishEvent(PUBSUB_NAME, TOPIC_NAME, objectMapper.writeValueAsBytes(order)).block();
            TimeUnit.MILLISECONDS.sleep(100);

        }
    }
}
