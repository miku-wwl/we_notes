package com.weilai.redisson.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/send/{id}")
    public String send(@PathVariable String id) {
        RTopic topic = redissonClient.getTopic("myTopic");
        topic.publish("Hello Redis!" + id);
        return "Hello Redis!" + id;
    }
}
