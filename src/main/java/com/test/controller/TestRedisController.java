package com.test.controller;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.State;
import io.dapr.client.domain.StateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TestRedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping(value = "/redis-dapr")
    public String sendSMSToRedisDapr() throws Exception {
        DaprClient client = new DaprClientBuilder().build();
        int m = 0;
        Long start = System.nanoTime();
        while(m<100000){
            int k = new Random().nextInt();
            StateOptions options = new StateOptions(StateOptions.Consistency.STRONG, StateOptions.Concurrency.LAST_WRITE);
            client.saveState("storeredis", "test" + m, "etag", "hello word", options).block();
            State<String> savedState = client.getState("storeredis", "test" + k, String.class).block();
            System.out.println("Retrieved value from state store: " + savedState.getValue());
            m++;
        }
        Long end = System.nanoTime();
        System.out.println("all time : " + (end - start));
        client.close();
        return "OK";
    }
    @GetMapping(value = "/redis")
    public String sendSMSToRedis() throws Exception {
        int m = 0;
        Long start = System.nanoTime();
        while(m<100000){
            redisTemplate.convertAndSend("test" + m, "hello word");
            m++;
        }
        Long end = System.nanoTime();
        System.out.println("all time : " + (end - start));
        return "OK";
    }
}
