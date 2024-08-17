package com.imooc.log.stack.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * <h1>请求日志切面</h1>
 * */
@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
public class WebLogAspect {

    @Autowired
    private ObjectMapper mapper;
    private static final Integer PRINT_LOG_SIZE_LIMIT = 100;

    /** 以 controller 包下定义的所有请求为切入点 */
    @Pointcut("execution(public * com.imooc.log.stack.controller..*.*(..)))")
    public void webLog() { }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求参数相关信息
        log.debug("======================= Request Coming =======================");
        log.debug("URL: [{}]", request.getRequestURL().toString());
        log.debug("HTTP Method: [{}]", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.debug("Class Method: [{}].[{}]",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        // 打印请求入参
        String requestArgs = mapper.writeValueAsString(joinPoint.getArgs());
        if (requestArgs.length() > PRINT_LOG_SIZE_LIMIT) {
            log.trace("Request Args: [{}]", requestArgs);
        } else {
            log.debug("Request Args: [{}]", requestArgs);
        }
    }

    @After("webLog()")
    public void doAfter() throws Throwable {

        log.debug("======================= Request Done =======================");
        log.debug("");
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        StopWatch sw = StopWatch.createStarted();
        Object result = proceedingJoinPoint.proceed();

        String resultJson = mapper.writeValueAsString(result);
        if (resultJson.length() > PRINT_LOG_SIZE_LIMIT) {
            log.trace("Response Args: [{}]", resultJson);
        } else {
            log.debug("Response Args: [{}]", resultJson);
        }

        sw.stop();
        log.debug("Time Elapsed: [{}ms]", sw.getTime(TimeUnit.MILLISECONDS));
        return result;
    }
}
