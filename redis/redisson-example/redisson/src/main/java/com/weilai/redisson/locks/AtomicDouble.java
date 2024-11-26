package com.weilai.redisson.locks;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtomicDouble {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("AtomicDoubleExample init");
        redisson.getKeys().flushall();
        // 获取一个原子双精度浮点数实例
        RAtomicDouble atomicDouble = redisson.getAtomicDouble("myDouble");

        // 演示各种原子操作
        log.info("Initial value: {}", atomicDouble.get());

        // 减少当前值并返回旧值
        double oldValue = atomicDouble.getAndDecrement();
        log.info("After getAndDecrement: Old value = {}, New value = {}", oldValue, atomicDouble.get());

        // 增加当前值并返回旧值
        oldValue = atomicDouble.getAndIncrement();
        log.info("After getAndIncrement: Old value = {}, New value = {}", oldValue, atomicDouble.get());

        // 增加指定值并返回新值
        double newValue = atomicDouble.addAndGet(10.323);
        log.info("After addAndGet(10.323): New value = {}", newValue);

        // 比较并设置值
        boolean isSet = atomicDouble.compareAndSet(29.4, 412.91);
        log.info("After compareAndSet(29.4, 412.91): Set successful = {}, Current value = {}", isSet, atomicDouble.get());

        isSet = atomicDouble.compareAndSet(10.323, 412.91);
        log.info("After compareAndSet(10.323, 412.91): Set successful = {}, Current value = {}", isSet, atomicDouble.get());


        // 减少当前值并返回新值
        newValue = atomicDouble.decrementAndGet();
        log.info("After decrementAndGet: New value = {}", newValue);

        // 增加当前值并返回新值
        newValue = atomicDouble.incrementAndGet();
        log.info("After incrementAndGet: New value = {}", newValue);

        // 增加指定值并返回旧值
        oldValue = atomicDouble.getAndAdd(302.00);
        log.info("After getAndAdd(302.00): Old value = {}, New value = {}", oldValue, atomicDouble.get());

        // 减少当前值并返回旧值
        oldValue = atomicDouble.getAndDecrement();
        log.info("After getAndDecrement: Old value = {}, New value = {}", oldValue, atomicDouble.get());

        // 增加当前值并返回旧值
        oldValue = atomicDouble.getAndIncrement();
        log.info("After getAndIncrement: Old value = {}, New value = {}", oldValue, atomicDouble.get());
    }
}