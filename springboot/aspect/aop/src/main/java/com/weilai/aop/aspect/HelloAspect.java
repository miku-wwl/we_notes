package com.weilai.aop.aspect;

import com.weilai.aop.annotation.Hello;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
@Aspect
@Slf4j
public class HelloAspect {

    @Pointcut("@annotation(com.weilai.aop.annotation.Hello)")
    public void check() {
    }

    // 也可参考 https://github.com/miku-wwl/wetube/blob/main/src/main/java/com/weilai/wetube/aspect/DataLimitedAspect.java
    @Before("check() && @annotation(hello)")
    public void doBefore(JoinPoint joinPoint, Hello hello) {
        log.info("Hello World!");
    }
}