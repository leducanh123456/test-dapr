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
//    @Autowired
//    private RedisTemplate template;

//    @GetMapping(value = "/redis-spring")
//    public String sendSMSToRedisSpring() {
//        System.out.println("----------------START SEND-----------");
//        int k = new Random().nextInt();
//        template.opsForValue().set("test" + k, "hello world");
//        System.out.println("----------------END SEND-------------");
//        // In ra màn hình Giá trị của key  trong Redis
//        System.out.println("Value of key : " + template.opsForValue().get("test" + k));
//        return "OK";
//    }

    @GetMapping(value = "/redis-dapr")
    public String sendSMSToRedisDapr() throws Exception {
        DaprClient client = new DaprClientBuilder().build();
        int k = new Random().nextInt();
        StateOptions options = new StateOptions(StateOptions.Consistency.STRONG, StateOptions.Concurrency.LAST_WRITE);
        client.saveState("redis-state", "test" + k, "etag","hello word", options).block();
        State<String> savedState = client.getState("redis-state", "test" + k, String.class).block();
        System.out.println("Retrieved value from state store: " + savedState.getValue());
        client.close();
        return "OK";
    }
}
