package com.imooc.log.stack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h1>工程启动入口</h1>
 * -Xms1024M -Xmx1024M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * */
@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class LogStackApplication {

    public static void main(String[] args) {

        SpringApplication.run(LogStackApplication.class, args);

//        try {
//            SpringApplication.run(LogStackApplication.class, args);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        String config = null;
//        log.info("current config is: [{}]", config.length());
    }
}
