package com.weilai.exception.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ExceptionController {

    @GetMapping("/hello")
    public void exception() {
        throw new RuntimeException("hello");
    }
}