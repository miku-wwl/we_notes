package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

@Slf4j
@Service
public class ListMultimap {
    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        log.info("ListMultimap init");

        RListMultimap<String, Integer> multimap = redissonClient.getListMultimap("ListMultimap");
        multimap.put("1", 1);
        multimap.put("1", 2);
        multimap.put("1", 3);
        multimap.put("2", 5);
        multimap.put("2", 6);
        multimap.put("4", 7);

        RList<Integer> values1 = multimap.get("1");
        RList<Integer> values2 = multimap.get("2");
        log.info("values1: {}", values1.readAll());
        log.info("values2: {}", values2.readAll());


        boolean hasEntry = multimap.containsEntry("1", 3);
        log.info("hasEntry: {}", hasEntry);

        Collection<Entry<String, Integer>> entries = multimap.entries();
        entries.forEach(it -> {
            log.info("key: {}, value: {}", it.getKey(), it.getValue());
        });

        Collection<Integer> values = multimap.values();
        values.forEach(it -> {
            log.info("value: {}", it);
        });

        boolean isRemoved = multimap.remove("1", 3);
        log.info("isRemoved: {}", isRemoved);
        List<Integer> removedValues = multimap.removeAll("1");
        log.info("removedValues: {}", removedValues);

        Collection<? extends Integer> newValues = Arrays.asList(5, 6, 7, 8, 9);
        boolean isNewKey = multimap.putAll("5", newValues);
        log.info("isNewKey: {}", isNewKey);

        List<Integer> oldValues = multimap.replaceValues("2", newValues);
        log.info("oldValues: {}", oldValues);

        List<Integer> allValues = multimap.getAll("2");
        log.info("allValues: {}", allValues);

        long keysRemoved = multimap.fastRemove("2", "32");
        log.info("keysRemoved: {}", keysRemoved);
        log.info("multimap.getAll(\"2\"): {}", multimap.getAll("2"));
    }
}