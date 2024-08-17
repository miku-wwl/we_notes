package com.imooc.log.stack.chapter4;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Logback 的使用</h1>
 * */
public class UseLogback {

    private static final Logger logger = LoggerFactory.getLogger(UseLogback.class);

    /**
     * <h2>支持占位符</h2>
     * */
    private static void log() {
        logger.info("this is slf4j + logback: [{}]", UseLogback.class.getName());
    }

    private static void levelLog() {

        logger.trace("slf4j + logback: [{}]", "trace");
        logger.debug("slf4j + logback: [{}]", "debug");
        logger.info("slf4j + logback: [{}]", "info");
        logger.warn("slf4j + logback: [{}]", "warn");
    }

    /**
     * <h2>打印 logback 的内部状态</h2>
     * */
    private static void printLogbackStatus() {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);
    }

    public static void main(String[] args) {
//        log();

        levelLog();

//        printLogbackStatus();
    }
}
