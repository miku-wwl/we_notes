package com.weilai.redisson.collections;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class Queues {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {

        RQueue<String> queue = redisson.getQueue("myQueue");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");

        log.info("queue.contains(\"1\") :{}", queue.contains("1"));
        log.info("queue.peek() :{}", queue.peek());
        log.info("queue.poll() :{}", queue.poll());
        log.info("queue.element() :{}", queue.element());

        List<String> strs = queue.readAll();
        log.info("strs:{}", strs);

        boolean removedValue = queue.remove("1");
        log.info("removedValue:{}", removedValue);
        queue.removeAll(Arrays.asList("1", "2", "3"));
        log.info("strs:{}", queue.readAll());

        log.info("queue.containsAll(Arrays.asList(\"4\", \"1\", \"0\")) :{}", queue.containsAll(Arrays.asList("4", "1", "0")));

        List<String> secondList = new ArrayList<>();
        secondList.add("44");
        secondList.add("55");
        queue.addAll(secondList);

        RQueue<String> secondQueue = redisson.getQueue("mySecondQueue");

        queue.pollLastAndOfferFirstTo(secondQueue.getName());
        log.info("queue strs:{}", queue.readAll());
        log.info("secondQueue strs:{}", secondQueue.readAll());


    }

}