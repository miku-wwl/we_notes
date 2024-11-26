package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BitSet {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("BitSetExample init");
        redisson.getKeys().flushall();
        // 获取一个 BitSet 实例
        RBitSet bs = redisson.getBitSet("testbitset");

        bs.set(0, 5);
        log.info("bs: {}", bs.toString());

        bs.clear(0, 1);
        log.info("bs: {}", bs.toString());

        // 获取 BitSet 的长度
        long length = bs.length();
        log.info("BitSet length: {}", length);

        // 清除所有位
        bs.clear();
        log.info("bs: {}", bs.toString());

        // 设置位 28 为 1
        bs.set(28);
        log.info("bs: {}", bs.toString());

        // 获取 BitSet 的长度
        length = bs.length();
        log.info("BitSet length: {}", length);

        // 获取位 28 的值
        boolean bit28 = bs.get(28);
        log.info("bs.get(28): {}", bit28);

        // 对 BitSet 进行按位取反
        bs.not();
        log.info("bs: {}", bs.toString());

        // 获取 BitSet 的基数（即设置为 1 的位的数量）
        long cardinality = bs.cardinality();
        log.info("BitSet cardinality: {}", cardinality);

        // 设置位 3 为 1
        bs.set(3, true);
        log.info("bs: {}", bs.toString());

        // 设置位 41 为 0
        bs.set(41, true);
        log.info("bs: {}", bs.toString());

        cardinality = bs.cardinality();
        log.info("BitSet cardinality: {}", cardinality);


        // 获取另一个 BitSet 实例
        RBitSet bs1 = redisson.getBitSet("testbitset1");
        bs1.set(3, 5);
        log.info("bs1: {}", bs1.toString());

        // 获取再一个 BitSet 实例
        RBitSet bs2 = redisson.getBitSet("testbitset2");
        bs2.set(4);
        bs2.set(10);
        log.info("bs2: {}", bs2.toString());

        // 对 BitSet1 和 BitSet2 进行按位与操作
        bs1.and(bs2.getName());
        log.info("bs1.and(bs2.getName()): {}", bs1.toString());

        // 对 BitSet1 和 BitSet2 进行按位或操作
        bs1.or(bs2.getName());
        log.info("bs1.or(bs2.getName()): {}", bs1.toString());

        // 对 BitSet1 和 BitSet2 进行按位异或操作
        bs1.xor(bs2.getName());
        log.info("bs1.xor(bs2.getName()): {}", bs1.toString());
    }
}