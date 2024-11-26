package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
@Service
public class Maps {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("Maps init");
        redisson.getKeys().flushall();
        RMap<String, Integer> map = redisson.getMap("myMap");
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        boolean contains = map.containsKey("a");
        log.info("contains {}", contains);


        Integer value = map.get("c");
        log.info("value {}", value);

        Integer valueSize = map.valueSize("c");
        log.info("valueSize {}", valueSize);


        Set<String> keys = new HashSet<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");


        Map<String, Integer> mapSlice = map.getAll(keys);
        log.info("mapSlice {}", mapSlice);

        // use read* methods to fetch all objects
        Set<String> allKeys = map.readAllKeySet();
        log.info("allKeys {}", allKeys);
        Collection<Integer> allValues = map.readAllValues();
        log.info("allValues {}", allValues);
        Set<Entry<String, Integer>> allEntries = map.readAllEntrySet();
        log.info("allEntries {}", allEntries);

        // use fast* methods when previous value is not required
        boolean isNewKey = map.fastPut("a", 100);
        log.info("isNewKey {}", isNewKey);

        boolean isNewKeyPut = map.fastPutIfAbsent("d", 33);
        log.info("isNewKeyPut {}", isNewKeyPut);
        long removedAmount = map.fastRemove("b");
        log.info("removedAmount {}", removedAmount);
    }
}