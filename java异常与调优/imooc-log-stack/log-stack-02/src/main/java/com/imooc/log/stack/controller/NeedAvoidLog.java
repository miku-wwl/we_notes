package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * <h1>需要规避的日志</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/avoid")
public class NeedAvoidLog {

    @PostMapping("/log")
    public Imoocer avoidLog(List<Imoocer> imoocers) {

        // 1. 避免大数据量日志的打印
        log.info("args imoocer: [{}]", imoocers); // 这种方式并不好
        if (imoocers.size() > 10) {
            log.info("args imoocer count: [{}]", imoocers.size());
        }

        // 2. 避免在循环中打日志, 特别是大循环
        imoocers.forEach(i -> log.info("imoocer name is: [{}]", i.getName()));

        // 3. 不要打没有意义的日志
        File file = new File(imoocers.get(0).getName());
        if (!file.exists()) {
            log.warn("file does not exist!");   // 没有意义
            log.warn("file does not exist: [{}]", imoocers.get(0).getName());
        }

        // 4. 如果日志什么都说明不了, 修改或删除
        double sumSalary = 0.0;
        for (Imoocer imoocer : imoocers) {
            sumSalary += imoocer.getSalary();
        }

        log.info("all imoocers sum salary is: [{}], [{}]", imoocers.size(), sumSalary);

        return null;
    }
}
