package com.weilai.consumer.feign;


import org.springframework.stereotype.Component;


@Component
public class BusinessFeignFallback implements BusinessFeign {
    @Override
    public String hello() {
        return "Fallback";
    }
}
