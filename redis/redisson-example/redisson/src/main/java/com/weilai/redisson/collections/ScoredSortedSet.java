package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ScoredSortedSet {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("ScoredSortedSet init");
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("mySortedSet");
        set.add(10, "1");
        set.add(5, "2");
        set.add(30, "3");

        Collection<String> allValues = set.readAll();
        log.info("allValues: {}", allValues);

        Map<String, Double> newValues = new HashMap<>();
        newValues.put("4", 40D);
        newValues.put("5", 50D);
        newValues.put("6", 60D);

        int newValuesAmount = set.addAll(newValues);
        log.info("newValuesAmount: {}", newValuesAmount);

        Double scoreResult = set.addScore("2", 10);
        log.info("scoreResult: {}", scoreResult);

        log.info("set.contains(\"4\"):{}", set.contains("4"));
        log.info("set.containsAll(Arrays.asList(\"3\", \"4\", \"5\")):{}", set.containsAll(Arrays.asList("3", "4", "5")));

        String firstValue = set.first();
        log.info("firstValue: {}", firstValue);
        String lastValue = set.last();
        log.info("lastValue: {}", lastValue);

        String polledFirst = set.pollFirst();
        log.info("polledFirst: {}", polledFirst);
        String polledLast = set.pollLast();
        log.info("polledLast: {}", polledLast);

        allValues = set.readAll();
        log.info("allValues: {}", allValues);
    }

}