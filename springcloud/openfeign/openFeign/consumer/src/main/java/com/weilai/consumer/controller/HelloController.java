package com.weilai.consumer.controller;


import com.weilai.consumer.feign.BusinessFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class HelloController {

    @Autowired
    private BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String hello() {
        return businessFeign.hello();
    }
}
