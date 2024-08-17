package com.imooc.log.stack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.log.stack.vo.Imoocer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>日志需要能够对你的业务逻辑进行解释</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/explain")
public class UseLogExplainLogic {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public UseLogExplainLogic(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @SneakyThrows
    @PostMapping("/logic")
    public Imoocer logic(List<Imoocer> imoocers) {

        // 1. 对请求参数进行记录, 方便对系统的调试
        if (imoocers.size() > 10) {
            log.debug("request coming in logic, args count: [{}], args: [{}]",
                    imoocers.size(), mapper.writeValueAsString(
                            imoocers.stream().map(Imoocer::getName).collect(Collectors.toList())
                    ));
        } else {
            log.debug("request coming in logic, args: [{}]",
                    mapper.writeValueAsString(imoocers));
        }

        List<Imoocer> validImoocers = new ArrayList<>(imoocers.size());
        for (int i = 0; i != imoocers.size(); ++i) {
            Imoocer cur = imoocers.get(i);
            if (null == cur || StringUtils.isBlank(cur.getName()) || cur.getAge() <= 0) {
                // 2. 使用 warn 记录下脏数据, 警告信息
                log.warn("args has some error: [index={}], [imoocer={}]",
                        i, mapper.writeValueAsString(cur));
                continue;
            }
            validImoocers.add(cur);
        }

        // 3. 发起 HTTP 请求
        ResponseEntity<Imoocer> entity = null;
        try {
            entity = restTemplate.getForEntity("www.imooc.com", Imoocer.class,
                    validImoocers);
            log.info("call imooc website with args and response: [count={}], [{}]",
                    validImoocers.size(), mapper.writeValueAsString(entity.getBody()));
        } catch (RestClientException ex) {
            log.error("....", ex);
//            throw new RuntimeException("call imooc website error", ex);
        }

        // 4. 远程过程调用
        callRemoteService(entity.getBody());
        log.trace("call remote service: [func={}], [args={}]", "callRemoteService",
                mapper.writeValueAsString(entity.getBody()));

        log.debug("response logic is: [{}]", mapper.writeValueAsString(entity.getBody()));
        return entity.getBody();
    }

    private void callRemoteService(Imoocer imoocer) {
        // ...
    }
}
