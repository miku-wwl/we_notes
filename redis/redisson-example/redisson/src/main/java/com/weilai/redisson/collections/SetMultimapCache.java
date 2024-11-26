package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RSetMultimapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SetMultimapCache {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("SetMultimapCache init");
        redisson.getKeys().flushall();
        RSetMultimapCache<String, Integer> multimap = redisson.getSetMultimapCache("SetMultimapCache");
        multimap.put("1", 1);
        multimap.put("1", 2);
        multimap.put("1", 3);
        multimap.put("2", 5);
        multimap.put("2", 6);
        multimap.put("4", 7);

        // set ttl = 10 seconds
        multimap.expireKey("1", 1, TimeUnit.SECONDS);

        RSet<Integer> values1 = multimap.get("1");
        RSet<Integer> values2 = multimap.get("2");

        log.info("values1: {}", values1.readAll());
        log.info("values2: {}", values2.readAll());

        Thread.sleep(2000);
        RSet<Integer> values3 = multimap.get("1");
        log.info("values3: {}", values3.readAll());

        boolean hasEntry = multimap.containsEntry("1", 3);
        log.info("hasEntry: {}", hasEntry);

        Collection<Entry<String, Integer>> entries = multimap.entries();
        entries.forEach(it -> {
            log.info("it.key: {}, it.value: {}", it.getKey(), it.getValue());
        });

        Collection<Integer> values = multimap.values();
        values.forEach(it -> {
            log.info("it.value: {}", it);
        });

        boolean isRemoved = multimap.remove("1", 3);
        log.info("isRemoved: {}", isRemoved);

        Set<Integer> removedValues = multimap.removeAll("1");
        log.info("removedValues: {}", removedValues);

        Collection<? extends Integer> newValues = Arrays.asList(5, 6, 7, 8, 9);
        boolean isNewKey = multimap.putAll("5", newValues);
        log.info("isNewKey: {}", isNewKey);

        Set<Integer> oldValues = multimap.replaceValues("2", newValues);
        log.info("oldValues: {}", oldValues);

        Set<Integer> allValues = multimap.getAll("2");
        log.info("allValues: {}", allValues);

        long keysRemoved = multimap.fastRemove("2", "32");
        log.info("keysRemoved: {}", keysRemoved);

        entries = multimap.entries();
        entries.forEach(it -> {
            log.info("it.key: {}, it.value: {}", it.getKey(), it.getValue());
        });
    }
}