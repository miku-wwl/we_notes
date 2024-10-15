``` java
package com.weilai.aeron;

import com.weilai.aeron.service.GatewayService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class AeronApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AeronApplication.class, args);
        GatewayService gatewayService = context.getBean(GatewayService.class);
        // 法一、同步方法
        // gatewayService.start();
        // 法二、同步方法
        // Thread thread = new Thread(() -> {
        //     try {
        //         gatewayService.start();
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // });
        // thread.start();
        // 法三、线程池
        // ExecutorService executor = Executors.newSingleThreadExecutor();
        // executor.submit(() -> {
        //     try {
        //         gatewayService.start();
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // });
        // 如果不再需要 ExecutorService，可以关闭它
        // executor.shutdown();
        // }

        // 法四、CompletableFuture
        // CompletableFuture.runAsync(() -> {
        //     try {
        //         gatewayService.start();
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // });
    }
}
```