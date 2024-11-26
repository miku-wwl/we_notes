package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Reference {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("Reference init");
        redisson.getKeys().flushall();
        // 获取或创建一个名为 "myMap" 的 RMap 对象
        RMap<String, RBucket<String>> data = redisson.getMap("Reference");

        // 创建一个名为 "myObject" 的 RBucket 对象，并设置其值
        RBucket<String> myObjectBucket = redisson.getBucket("myObjectBucket");
        myObjectBucket.set("5");
        myObjectBucket.set("7"); // 覆盖之前的值

        // 将 RBucket 对象放入 RMap 中
        data.put("bucket", myObjectBucket);

        // 从 RMap 中获取 RBucket 对象
        RBucket<String> bucket = data.get("bucket");

        // 输出 RBucket 中的值
        if (bucket != null) {
            String value = bucket.get();
            log.info("Value in the bucket: {}", value);
        } else {
            log.warn("Bucket not found in the map.");
        }
    }
}