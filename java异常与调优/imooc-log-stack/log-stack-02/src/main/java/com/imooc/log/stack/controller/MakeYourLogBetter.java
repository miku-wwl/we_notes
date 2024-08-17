package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <h1>对日志合理性、正确性、必要性的分析</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/make")
public class MakeYourLogBetter {

    /** 在 SomeConf 中定义的 */
    final RestTemplate restTemplate;

    public MakeYourLogBetter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/better/log")
    public Imoocer betterLog(List<Imoocer> imoocers) {

        // 1. HTTP 请求的入参和结果
        ResponseEntity<Imoocer> entity =
                restTemplate.getForEntity("www.imooc.com",
                        Imoocer.class, imoocers.get(0));
        // 也需要酌情考虑这里的可行性，如果入参和结果都很大，那么，只记录一些 “核心” 的信息就可以了
        log.info("call imooc website with args and response: [{}], [{}]",
                imoocers.get(0), entity.getBody());

        // 2. 程序异常的原因
        int num = 0;
        try {
            num = Integer.parseInt(imoocers.get(0).getName());
        } catch (IllegalArgumentException ex) {
            log.error("parse int value error for imooc(index 0) name: [{}]",
                    imoocers.get(0).getName());
            // ...
        }

        // 3. 远程接口调用（HTTP 或 RPC）情况 -- 与第一种类似, 不再重复举例子

        // 4. 特殊的条件分支
        num = statistics(imoocers);

        return null;
    }

    private int statistics(List<Imoocer> imoocers) {

        int greaterThan20k = 0;

        for (Imoocer imoocer : imoocers) {
            if (null != imoocer.getSalary() && imoocer.getSalary() >= 20000.0) {
                ++greaterThan20k;
                if (imoocer.getSalary() > 50000.0) {
                    log.info("imoocer salary greater than 50k: [{}]", imoocer);
                }
            }
        }

        return greaterThan20k;
    }
}
