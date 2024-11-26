package com.weilai.redisson.locks;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CountDownLatch {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("CountDownLatch init");
        redisson.getKeys().flushall();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        final RCountDownLatch latch = redisson.getCountDownLatch("latch1");
        latch.trySetCount(5);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("execute latch.getCount() = {}", latch.getCount());
                latch.countDown();
            }

        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean await = latch.await(550, TimeUnit.MILLISECONDS);
                    log.info("await = {}", await);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        log.info("latch.getCount() = {}", latch.getCount());

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}