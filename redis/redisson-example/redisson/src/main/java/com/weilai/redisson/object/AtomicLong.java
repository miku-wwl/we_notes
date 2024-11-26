package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtomicLong {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("AtomicLong init");
        redisson.getKeys().flushall();
        // 获取一个原子长整型实例
        RAtomicLong atomicLong = redisson.getAtomicLong("myLong");

        // 演示各种原子操作
        log.info("Initial value: {}", atomicLong.get());

        // 减少当前值并返回旧值
        long oldValue = atomicLong.getAndDecrement();
        log.info("After getAndDecrement: Old value = {}, New value = {}", oldValue, atomicLong.get());

        // 增加当前值并返回旧值
        oldValue = atomicLong.getAndIncrement();
        log.info("After getAndIncrement: Old value = {}, New value = {}", oldValue, atomicLong.get());

        // 增加指定值并返回新值
        long newValue = atomicLong.addAndGet(10L);
        log.info("After addAndGet(10): New value = {}", newValue);

        // 比较并设置值
        boolean isSet = atomicLong.compareAndSet(29, 412);
        log.info("After compareAndSet(29, 412): Set successful = {}, Current value = {}", isSet, atomicLong.get());

        // 减少当前值并返回新值
        newValue = atomicLong.decrementAndGet();
        log.info("After decrementAndGet: New value = {}", newValue);

        // 增加当前值并返回新值
        newValue = atomicLong.incrementAndGet();
        log.info("After incrementAndGet: New value = {}", newValue);

        // 增加指定值并返回旧值
        oldValue = atomicLong.getAndAdd(302L);
        log.info("After getAndAdd(302): Old value = {}, New value = {}", oldValue, atomicLong.get());

        // 减少当前值并返回旧值
        oldValue = atomicLong.getAndDecrement();
        log.info("After getAndDecrement: Old value = {}, New value = {}", oldValue, atomicLong.get());

        // 增加当前值并返回旧值
        oldValue = atomicLong.getAndIncrement();
        log.info("After getAndIncrement: Old value = {}, New value = {}", oldValue, atomicLong.get());
    }
}