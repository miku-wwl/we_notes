package com.weilai.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;


@SpringBootApplication
@EnableFeignClients("com.weilai.consumer.feign")
public class ConsumerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("env :{}", env);
        LOG.info("启动成功！");
    }
}
