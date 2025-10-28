package com.weilai.h2webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class H2WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2WebfluxApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, H2!";
    }
}
