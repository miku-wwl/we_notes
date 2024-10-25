package com.weilai.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "provider", fallback = BusinessFeignFallback.class)
public interface BusinessFeign {
    @GetMapping("/provider/hello")
    String hello();
}
