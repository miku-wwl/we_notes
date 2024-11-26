package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RListMultimapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map.Entry;

@Slf4j
@Service
public class ListMultimapCache {

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("redissonClient init");
        redissonClient.getKeys().flushall();

        RListMultimapCache<String, Integer> multimap = redissonClient.getListMultimapCache("ListMultimapCache");
        multimap.put("1", 1);
        multimap.put("1", 2);
        multimap.put("1", 3);
        multimap.put("2", 5);
        multimap.put("2", 6);
        multimap.put("4", 7);

        // set ttl = 10 seconds
        multimap.expireKey("1", 1, TimeUnit.SECONDS);

        RList<Integer> values1 = multimap.get("1");
        RList<Integer> values2 = multimap.get("2");
        RList<Integer> values4 = multimap.get("4");

        Thread.sleep(2000);

        log.info("value1:{}", values1.readAll());
        log.info("value2:{}", values2.readAll());
        log.info("value4:{}", values4.readAll());

        boolean hasEntry = multimap.containsEntry("1", 3);
        log.info("hasEntry:{}", hasEntry);

        Collection<Entry<String, Integer>> entries = multimap.entries();
        entries.forEach(it -> {
            log.info("key:{}, value:{}", it.getKey(), it.getValue());
        });

        Collection<Integer> values = multimap.values();
        values.forEach(it -> {
            log.info("value:{}", it);
        });

        boolean isRemoved = multimap.remove("1", 3);
        log.info("isRemoved:{}", isRemoved);
        log.info("removedValues:{}", multimap.removeAll("1"));

        List<Integer> newValues = Arrays.asList(5, 6, 7, 8, 9);
        boolean isNewKey = multimap.putAll("5", newValues);
        log.info("isNewKey:{}", isNewKey);
        log.info("newValues:{}", multimap.get("5"));

        List<Integer> oldValues = multimap.replaceValues("2", newValues);
        log.info("allValues:{}", multimap.getAll("2"));

        long keysRemoved = multimap.fastRemove("2", "32");
        log.info("keysRemoved:{}", keysRemoved);
        log.info("allValues:{}", multimap.getAll("2"));
    }

}