package com.example.grpcdemo.server;

import io.grpc.BindableService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GrpcServerTemplate {
    private static final Logger logger = Logger.getLogger(GrpcServerTemplate.class.getName());
    private static final int DEFAULT_PORT = 50051;
    private static final int DEFAULT_THREAD_POOL_SIZE = 2;
    private static final int SHUTDOWN_TIMEOUT = 30; // 关闭超时时间（秒）

    private final Server server;
    private final ExecutorService executorService;

    public GrpcServerTemplate(BindableService service) {
        this(DEFAULT_PORT, DEFAULT_THREAD_POOL_SIZE, service);
    }

    public GrpcServerTemplate(int port, BindableService service) {
        this(port, DEFAULT_THREAD_POOL_SIZE, service);
    }

    public GrpcServerTemplate(int port, int threadPoolSize, BindableService service) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .executor(executorService)
                .addService(service) // 注册服务实现
                .build();
        registerShutdownHook();
    }

    public void start() throws IOException {
        if (!server.isShutdown()) {
            server.start();
            logger.info("Server started, listening on " + server.getPort());
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void stop() throws InterruptedException {
        if (server != null && !server.isShutdown()) {
            server.shutdown().awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        }
        executorService.shutdown();
        logger.info("Server shut down completely");
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                this.stop();
            } catch (InterruptedException e) {
                server.shutdownNow();
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }
}