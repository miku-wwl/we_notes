package com.weilai.executor;

import com.weilai.executor.service.DataProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ExecutorApplication implements CommandLineRunner {

    @Autowired
    private DataProcessService dataProcessService;

    public static void main(String[] args) {
        SpringApplication.run(ExecutorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataProcessService.process();
        dataProcessService.process2();
    }
}
