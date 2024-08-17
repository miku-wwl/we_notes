package com.imooc.log.stack.chapter4;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * <h1>动态构造 Logger 对象</h1>
 * */
@SuppressWarnings("all")
public class LogbackHolder {

    /**
     * <h2>根据名称获取 logger 实例</h2>
     * */
    public static Logger getLogger(String name) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 如果没有创建 logger
        if (loggerContext.exists(name) == null) {
            // 自己动态构造 logger 对象
            return buildLogger(name);
        }

        return loggerContext.getLogger(name);
    }

    /**
     * <h2>动态构造 logger 实例</h2>
     * */
    private static Logger buildLogger(String name) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(name);

        // 配置 rollingFileAppender
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setName(name);
        rollingFileAppender.setContext(loggerContext);

        // 配置 rollingPolicy
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setFileNamePattern("/tmp/log/" + name + ".%d{yyyyMM}.log");
        rollingPolicy.setParent(rollingFileAppender);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.start();

        // 配置 encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setCharset(StandardCharsets.UTF_8);
        encoder.setPattern("%msg%n");
        encoder.setContext(loggerContext);
        encoder.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.start();

        // 配置 logger
        logger.addAppender(rollingFileAppender);
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);

        return logger;
    }

    public static void main(String[] args) {

        getLogger("qinyi").info("imooc qinyi use logback...");
    }
}
