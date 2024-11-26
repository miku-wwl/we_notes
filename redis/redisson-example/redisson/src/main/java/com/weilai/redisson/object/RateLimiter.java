package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class RateLimiter {

    @Autowired
    private RedissonClient redisson;

    /**
     * 初始化速率限制器
     */
    @PostConstruct
    public void init() {
        log.info("RateLimiter init");
        redisson.getKeys().flushall();
        RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");

        // 设置限流规则：每2秒最多允许1个请求
        boolean setRateSuccess = rateLimiter.trySetRate(RateType.OVERALL, 1, 2, RateIntervalUnit.SECONDS);
        if (!setRateSuccess) {
            log.error("Failed to set rate for the rate limiter.");
            return;
        }

        // 模拟两个请求尝试获取限流许可
        CountDownLatch latch = new CountDownLatch(2);

        // 第一个请求
        acquirePermit(rateLimiter, latch);

        // 第二个请求
        Thread thread = new Thread(() -> acquirePermit(rateLimiter, latch));
        thread.start();

        try {
            // 等待所有请求完成
            latch.await();
        } catch (InterruptedException e) {
            log.error("Latch await interrupted", e);
        }
    }

    /**
     * 尝试获取限流许可
     *
     * @param rateLimiter 速率限制器实例
     * @param latch       计数器锁
     */
    private void acquirePermit(RRateLimiter rateLimiter, CountDownLatch latch) {
        try {
            // 尝试获取1个许可
            boolean acquired = rateLimiter.tryAcquire();
            log.info("acquired:{}", acquired);
        } finally {
            latch.countDown();
        }
    }
}