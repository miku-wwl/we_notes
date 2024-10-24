package com.weilai.pubsub.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPubImpl implements Publish {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void pub(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
