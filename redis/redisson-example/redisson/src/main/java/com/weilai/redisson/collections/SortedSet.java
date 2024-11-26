package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class SortedSet {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("SortedSet init");
        redisson.getKeys().flushall();
        RSortedSet<String> sortedSet = redisson.getSortedSet("mySortedSet");
        sortedSet.add("1");
        sortedSet.add("6");
        sortedSet.add("3");

        for (String str : sortedSet) {
            log.info("str:{}", str);
        }

        String firstValue = sortedSet.first();
        log.info("firstValue:{}", firstValue);
        String lastValue = sortedSet.last();
        log.info("lastValue:{}", lastValue);

        boolean removedValue = sortedSet.remove("1");
        log.info("removedValue:{}", removedValue);
        log.info("sortedSet.removeAll(Arrays.asList(\"1\", \"2\", \"3\")):{}",  sortedSet.removeAll(Arrays.asList("1", "2", "3")));
        log.info(" sortedSet.containsAll(Arrays.asList(\"4\", \"1\", \"0\")):{}", sortedSet.containsAll(Arrays.asList("4", "1", "0")));
    }
}