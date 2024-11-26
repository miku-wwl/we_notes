package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class Sets {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("Sets init");
        redisson.getKeys().flushall();
        RSet<String> set = redisson.getSet("sets");
        set.add("1");
        set.add("2");
        set.add("3");

        log.info("set.contains(\"1\") :{}", set.contains("1"));
        log.info("set :{}", set.readAll());

        boolean removedValue = set.remove("1");
        log.info("removedValue :{}", removedValue);

        set.removeAll(Arrays.asList("1", "2", "3"));
        log.info("set :{}", set.readAll());

        log.info("set.containsAll(Arrays.asList(\"4\", \"1\", \"0\")) :{}", set.containsAll(Arrays.asList("4", "1", "0")));

        set.add("11");
        set.add("22");
        set.add("33");
        String randomRemovedValue = set.removeRandom();
        log.info("randomRemovedValue :{}", randomRemovedValue);

        String randomValue = set.random();
        log.info("randomValue :{}", randomValue);

        log.info("set :{}", set.readAll());
    }

}