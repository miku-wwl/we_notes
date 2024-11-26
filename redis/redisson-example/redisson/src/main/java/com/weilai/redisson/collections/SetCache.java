package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RSetCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SetCache {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("SetCache init");
        redisson.getKeys().flushall();
        RSetCache<String> setCache = redisson.getSetCache("SetCache");

        // with ttl = 20 seconds
        boolean isAdded = setCache.add("1", 1, TimeUnit.SECONDS);
        // store value permanently
        setCache.add("2");

        Thread.sleep(2000);

        log.info("setCache.contains(\"1\"): {}", setCache.contains("1"));
        log.info("setCache: {}", setCache.readAll());

        boolean removedValue = setCache.remove("1");
        log.info("removedValue: {}", removedValue);

        setCache.removeAll(Arrays.asList("1", "2", "3"));
        log.info("setCache: {}", setCache.readAll());

        log.info("setCache.containsAll(Arrays.asList(\"4\", \"1\", \"0\")): {}", setCache.containsAll(Arrays.asList("4", "1", "0")));

        RSet<String> secondsSet = redisson.getSet("mySecondsSet");
        Set<String> allValues = secondsSet.readAll();
        log.info("allValues: {}", allValues);
    }
}
