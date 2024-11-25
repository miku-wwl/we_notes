package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MapReadWriteThrough {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("MapReadWriteThrough init");
    }
}