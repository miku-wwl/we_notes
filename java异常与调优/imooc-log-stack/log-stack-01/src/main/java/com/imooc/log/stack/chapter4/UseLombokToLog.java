package com.imooc.log.stack.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>使用 lombok 工具打日志</h1>
 * */
@Slf4j(topic = "qinyi")
public class UseLombokToLog {

    public static void main(String[] args) {

        log.info("use lombok to log: [{}]", UseLombokToLog.class.getName());
    }
}
