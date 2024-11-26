package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HyperLogLog {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("HyperLogLogExample init");
        redisson.getKeys().flushall();
        // 获取一个 HyperLogLog 实例
        RHyperLogLog<String> hyperLogLog = redisson.getHyperLogLog("hyperLogLog");

        // 构造大数据集
        int totalElements = 1_000_000; // 100w个元素
        Random random = new Random();
        Set<String> uniqueElements = new HashSet<>();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < totalElements; i++) {
            String element = "element-" + random.nextInt(totalElements);
            list.add(element);
            uniqueElements.add(element);
        }
        hyperLogLog.addAll(list);

        log.info("Added {} elements to HyperLogLog", totalElements);

        // 获取 HyperLogLog 的估计元素数量
        long estimatedCount = hyperLogLog.count();
        log.info("Estimated count in HyperLogLog: {}", estimatedCount);

        // 计算实际的唯一元素数量
        int actualUniqueElements = uniqueElements.size();
        log.info("Actual unique elements: {}", actualUniqueElements);

        // 计算估计误差
        double errorRate = Math.abs(estimatedCount - actualUniqueElements) / (double) actualUniqueElements * 100;
        log.info("Error rate: {}", errorRate);
    }
}