package com.weilai.executor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataProcessService {
    @Async(value = "dataProcessExecutor")
    public void process() {
        var businessId = 1;
        var groupIds = 2;
        var requestDate = 3;

        log.info("process start, threadName: {}, businessId: {}, groupIds: {}, requestDate: {}",
                Thread.currentThread().getName(), businessId, groupIds, requestDate);
    }

    @Autowired
    private ThreadPoolTaskExecutor mvcTaskExecutor;

    public void process2() {
        var businessId = 1;
        var groupIds = 2;
        var requestDate = 3;
        log.info("process2 start, threadName: {}, businessId: {}, groupIds: {}, requestDate: {}",
                Thread.currentThread().getName(), businessId, groupIds, requestDate);
        mvcTaskExecutor.execute(() -> {
            log.info("process2 start, threadName: {}, businessId: {}, groupIds: {}, requestDate: {}",
                    Thread.currentThread().getName(), businessId, groupIds, requestDate);
        });
    }
}