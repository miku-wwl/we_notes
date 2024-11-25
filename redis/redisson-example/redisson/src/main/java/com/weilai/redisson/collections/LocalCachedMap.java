package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.LocalCachedMapOptions;
import org.redisson.api.options.LocalCachedMapOptions.EvictionPolicy;
import org.redisson.api.options.LocalCachedMapOptions.ExpirationEventPolicy;
import org.redisson.api.options.LocalCachedMapOptions.SyncStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
@Service
public class LocalCachedMap {
    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        log.info("LocalCachedMap init");
        // NOTE: Key and Value can be of any type, eg. Object,Integer,Boolean etc.
        final LocalCachedMapOptions<String, Integer> options = LocalCachedMapOptions
                .<String, Integer>name("myMap")
                .cacheSize(10000)
                .maxIdle(Duration.ofSeconds(60))
                .timeToLive(Duration.ofSeconds(60))
                .evictionPolicy(EvictionPolicy.LFU)
                .syncStrategy(SyncStrategy.UPDATE)
                .expirationEventPolicy(ExpirationEventPolicy.SUBSCRIBE_WITH_KEYSPACE_CHANNEL);

        // Create a Local Cached Map with options
        RLocalCachedMap<String, Integer> cachedMap = redissonClient.getLocalCachedMap(options);
        cachedMap.put("a", 1);
        cachedMap.put("b", 2);
        cachedMap.put("c", 3);

        boolean contains = cachedMap.containsKey("a");
        log.info("contains {}", contains);

        Integer value = cachedMap.get("c");
        log.info("value {}", value);

        Integer valueSize = cachedMap.valueSize("c");
        log.info("valueSize {}", valueSize);

        Set<String> keys = new HashSet<String>();

        keys.add("a");
        keys.add("b");
        keys.add("c");
        Map<String, Integer> mapSlice = cachedMap.getAll(keys);
        log.info("mapSlice {}", mapSlice);


        // use read* methods to fetch all objects
        Set<String> allKeys = cachedMap.readAllKeySet();
        log.info("allKeys {}", allKeys);

        Collection<Integer> allValues = cachedMap.readAllValues();
        allValues.forEach(it -> {
            log.info("value: {}", it);
        });

        Set<Entry<String, Integer>> allEntries = cachedMap.readAllEntrySet();
        allEntries.forEach(it -> {
            log.info("key: {}, value: {}", it.getKey(),it.getValue());
        });


        // use fast* methods when previous value is not required
        boolean isNewKey = cachedMap.fastPut("a", 100);
        log.info("isNewKey {}", isNewKey);

        boolean isNewKeyPut = cachedMap.fastPutIfAbsent("d", 33);
        log.info("isNewKeyPut {}", isNewKeyPut);

        long removedAmount = cachedMap.fastRemove("b");
        log.info("removedAmount {}", removedAmount);
    }
}
