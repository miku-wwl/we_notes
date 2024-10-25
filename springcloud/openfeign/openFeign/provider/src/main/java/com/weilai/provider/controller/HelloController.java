package com.weilai.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }
}
