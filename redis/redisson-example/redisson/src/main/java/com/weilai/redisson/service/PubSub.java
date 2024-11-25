package com.weilai.redisson.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PubSub {
    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        RTopic topic = redissonClient.getTopic("myTopic");
        topic.addListener(new StatusListener() {
            @Override
            public void onSubscribe(String s) {
                log.info("onSubscribe:{}", s);
            }

            @Override
            public void onUnsubscribe(String s) {
                log.info("onUnsubscribe:{}", s);
            }
        });

        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                log.info("1. Receive message {} from {}", s, charSequence);
            }
        });

        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                log.info("2. Receive message {} from {}", s, charSequence);
            }
        });

    }
}