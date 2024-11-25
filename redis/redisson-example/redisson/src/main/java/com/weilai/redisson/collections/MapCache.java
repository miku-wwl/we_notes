package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MapCache {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("MapCache init");
        RMapCache<String, Integer> mapCache = redisson.getMapCache("mapcache");

        // with ttl = 10 seconds
        Integer prevValue = mapCache.put("1", 10, 10, TimeUnit.SECONDS);
        log.info("prevValue: {}", prevValue);
        // with ttl = 15 seconds and maxIdleTime = 5 seconds
        Integer prevValue2 = mapCache.put("2", 20, 15, TimeUnit.SECONDS, 5, TimeUnit.SECONDS);
        log.info("prevValue2: {}", prevValue2);
        // store value permanently
        Integer prevValue3 = mapCache.put("3", 30);
        log.info("prevValue3: {}", prevValue3);

        // with ttl = 30 seconds
        Integer currValue = mapCache.putIfAbsent("4", 40, 30, TimeUnit.SECONDS);
        log.info("currValue: {}", currValue);

        // with ttl = 40 seconds and maxIdleTime = 10 seconds
        Integer currValue2 = mapCache.putIfAbsent("5", 50, 40, TimeUnit.SECONDS, 10, TimeUnit.SECONDS);
        log.info("currValue2: {}", currValue2);

        // try to add new key-value permanently
        Integer currValue3 = mapCache.putIfAbsent("6", 60);
        log.info("currValue3: {}", currValue3);

        // use fast* methods when previous value is not required

        // with ttl = 20 seconds
        boolean isNewKey1 = mapCache.fastPut("7", 70, 20, TimeUnit.SECONDS);
        log.info("isNewKey1: {}", isNewKey1);

        // with ttl = 40 seconds and maxIdleTime = 20 seconds
        boolean isNewKey2 = mapCache.fastPut("8", 80, 40, TimeUnit.SECONDS, 20, TimeUnit.SECONDS);
        log.info("isNewKey2: {}", isNewKey2);
        // store value permanently
        boolean isNewKey3 = mapCache.fastPut("9", 90);
        log.info("isNewKey3: {}", isNewKey3);

        // try to add new key-value permanently
        boolean isNewKeyPut = mapCache.fastPutIfAbsent("10", 100);
        log.info("isNewKeyPut: {}", isNewKeyPut);

        boolean contains = mapCache.containsKey("a");
        log.info("contains: {}", contains);

        Integer value = mapCache.get("c");
        log.info("value: {}", value);

        Integer updatedValue = mapCache.addAndGet("a", 32);
        log.info("updatedValue: {}", updatedValue);

        Integer valueSize = mapCache.valueSize("c");
        log.info("valueSize: {}", valueSize);

        Set<String> keys = new HashSet<>();
        keys.add("7");
        keys.add("8");
        keys.add("9");
        Map<String, Integer> mapSlice = mapCache.getAll(keys);
        log.info("mapSlice: {}", mapSlice);
    }
}