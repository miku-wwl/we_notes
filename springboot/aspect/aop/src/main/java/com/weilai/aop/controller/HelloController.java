package com.weilai.aop.controller;

import com.weilai.aop.annotation.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Hello
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
