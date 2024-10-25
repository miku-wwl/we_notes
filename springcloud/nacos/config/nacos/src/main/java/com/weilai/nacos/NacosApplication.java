package com.weilai.nacos;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class NacosApplication {
    private static final Logger LOG = LoggerFactory.getLogger(NacosApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NacosApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！");
    }
}
