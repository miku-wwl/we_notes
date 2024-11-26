package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BloomFilter {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("BloomFilter init");
        redisson.getKeys().flushall();
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloomFilter");
        bloomFilter.tryInit(100_000_000, 0.03);

        bloomFilter.add("a");
        bloomFilter.add("b");
        bloomFilter.add("c");
        bloomFilter.add("d");
        bloomFilter.add("e");

        bloomFilter.add("f");
        bloomFilter.add("g");
        bloomFilter.add("h");
        bloomFilter.add("i");
        bloomFilter.add("j");

        bloomFilter.add("k");
        bloomFilter.add("l");
        bloomFilter.add("m");
        bloomFilter.add("n");
        bloomFilter.add("o");

        bloomFilter.add("p");
        bloomFilter.add("q");
        bloomFilter.add("r");
        bloomFilter.add("s");
        bloomFilter.add("t");


        // 获取布隆过滤器的预期插入数量
        long expectedInsertions = bloomFilter.getExpectedInsertions();
        log.info("Expected insertions: {}", expectedInsertions);

        // 获取布隆过滤器的误判率
        double falseProbability = bloomFilter.getFalseProbability();
        log.info("False probability: {}", falseProbability);

        // 获取布隆过滤器的哈希迭代次数
        int hashIterations = bloomFilter.getHashIterations();
        log.info("Hash iterations: {}", hashIterations);

        // 检查元素 "a" 是否存在于布隆过滤器中
        boolean containsA = bloomFilter.contains("a");
        log.info("Element 'a' is in BloomFilter: {}", containsA);

        // 获取布隆过滤器中已插入的元素数量
        long count = bloomFilter.count();
        log.info("Number of elements in BloomFilter: {}", count);
    }
}