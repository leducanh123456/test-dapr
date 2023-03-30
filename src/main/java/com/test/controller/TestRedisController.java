package com.test.controller;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.State;
import io.dapr.client.domain.StateOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TestRedisController {

    @GetMapping(value = "/redis-dapr")
    public String sendSMSToRedisDapr() throws Exception {
        DaprClient client = new DaprClientBuilder().build();
        int k = new Random().nextInt();
        StateOptions options = new StateOptions(StateOptions.Consistency.STRONG, StateOptions.Concurrency.LAST_WRITE);
        client.saveState("storeredis", "test" + k, "etag", "hello word", options).block();
        State<String> savedState = client.getState("storeredis", "test" + k, String.class).block();
        System.out.println("Retrieved value from state store: " + savedState.getValue());
        client.close();
        return "OK";
    }
}
