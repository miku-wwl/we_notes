package com.weilai.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ProviderApplication {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProviderApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("env :{}", env);
        LOG.info("启动成功！");
    }
}
