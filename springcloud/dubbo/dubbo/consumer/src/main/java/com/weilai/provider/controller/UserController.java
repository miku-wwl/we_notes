package com.weilai.provider.controller;

import com.weilai.provider.dubbo.UserDubbo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consumer")
public class UserController {

    @DubboReference
    private UserDubbo userDubbo;

    @GetMapping("/user")
    public String getUserId() {
        log.info("userId={}", 666);
        return userDubbo.getUserId(666);
    }
}