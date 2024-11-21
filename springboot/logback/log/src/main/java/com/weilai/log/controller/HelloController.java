package com.weilai.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.info("hello world!");
        return "Hello World";
    }
}
