package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class Batch {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws ExecutionException, InterruptedException {
        log.info("Batch init");
        redisson.getKeys().flushall();
        // 创建一个批处理对象
        RBatch batch = redisson.createBatch(BatchOptions.defaults());

        // 批处理操作：向多个 map 中异步插入数据

        RFuture<Boolean> future1 = batch.getMap("test1").fastPutAsync("1", "2");
        RFuture<Boolean> future2 = batch.getMap("test2").fastPutAsync("2", "3");
        RFuture<Object> future3 = batch.getMap("test3").putAsync("2", "5");

        // 批处理操作：异步增加原子长整型的值
        RFuture<Long> future4 = batch.getAtomicLong("counter").incrementAndGetAsync();
        RFuture<Long> future5 = batch.getAtomicLong("counter").incrementAndGetAsync();

        // 注册回调函数，当未来对象完成时执行
        future4.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Future 4 completed with result: {}", result);
            } else {
                log.error("Future 4 failed with exception: ", exception);
            }
        });

        // 执行批处理操作
        BatchResult<?> batchResult = batch.execute();

        // 获取批处理操作的结果
        Long counter = (Long) batchResult.getResponses().get(3);
        log.info("Counter value after increment: {}", counter);

        // 验证 future4 的结果与批处理结果的一致性
        Long future4Result = future4.get();
        log.info("Future 4 result: {}", future4Result);
        log.info("Future 4 result equals batch result: {}", future4Result.equals(counter));
    }
}