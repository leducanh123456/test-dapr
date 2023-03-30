package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.Order;
import com.test.kafka.ProducerMessage;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class TestKafkaBindingController {

    @Autowired
    private ProducerMessage producerMessage;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/send-message")
    public String sendSMSToKafka() throws Exception {
        System.out.println("----------------START SEND-----------");
        String mes = "Sending message to kafka topic by cafeincode:" + new Random().nextInt(100);
        sendMessageBinding(mes);
        System.out.println("----------------END SEND-------------");
        return "OK";
    }

    public void sendMessageBinding(String message) throws Exception {
        String BINDING_NAME = "checkout";
        String BINDING_OPERATION = "create";
        int k = 0;
        DaprClient client = new DaprClientBuilder().build();
        while (k < 1) {
            Random random = new Random();
            int orderId = random.nextInt(1000 - 1) + 1;
            Order order = new Order(orderId);
            client.invokeBinding(BINDING_NAME, BINDING_OPERATION, order).block();
            k++;
        }
    }
}
