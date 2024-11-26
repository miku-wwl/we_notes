package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Slf4j
@Service
public class Bucket {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("Bucket init");
        redisson.getKeys().flushall();

        RBucket<String> bucket = redisson.getBucket("test");
        bucket.set("123");

        boolean isUpdated = bucket.compareAndSet("123", "4934");
        log.info("isUpdated: {}", isUpdated);

        String prevObject = bucket.getAndSet("321");
        log.info("prevObject: {}", prevObject);

        boolean isSet = bucket.setIfAbsent("901");
        log.info("isSet: {}", isSet);

        long objectSize = bucket.size();
        log.info("objectSize: {}", objectSize);

        bucket.set("value", Duration.ofSeconds(1));
        boolean isNewSet = bucket.setIfAbsent("nextValue", Duration.ofSeconds(1));
        log.info("isNewSet: {}", isNewSet);

        Thread.sleep(2000);
        isNewSet = bucket.setIfAbsent("nextValue", Duration.ofSeconds(10));
        log.info("isNewSet: {}", isNewSet);
    }
}