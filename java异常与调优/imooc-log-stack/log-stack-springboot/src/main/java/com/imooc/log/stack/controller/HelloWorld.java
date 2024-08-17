package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello-world")
public class HelloWorld {

    /**
     * 健康检查入口
     * http://127.0.0.1:8888/api/hello-world/imoocer
     * */
    @GetMapping("/imoocer")
    public Imoocer imoocer() {

        return new Imoocer("qinyi", 19, 400000.0);
    }
}
