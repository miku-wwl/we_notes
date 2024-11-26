package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class LexSortedSet {

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        log.info("redissonClient init");
        redissonClient.getKeys().flushall();
        RLexSortedSet set = redissonClient.getLexSortedSet("sortedSet");
        set.add("1");
        set.add("2");
        set.add("3");

        for (String string : set) {
            log.info("string in set: {}", string);
        }

        Set<String> newValues = new HashSet<>();
        newValues.add("4");
        newValues.add("5");
        newValues.add("6");
        set.addAll(newValues);

        log.info("set.contains(\"4\"): {}", set.contains("4"));
        log.info("set.containsAll(Arrays.asList(\"3\", \"4\", \"5\")): {}", set.containsAll(Arrays.asList("3", "4", "5")));

        String firstValue = set.first();
        log.info("firstValue: {}", firstValue);
        String lastValue = set.last();
        log.info("lastValue: {}", lastValue);

        String polledFirst = set.pollFirst();
        String polledLast = set.pollLast();

        log.info("set: {}", set.readAll());
    }
}
