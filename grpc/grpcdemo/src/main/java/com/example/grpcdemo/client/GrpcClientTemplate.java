package com.example.grpcdemo.client;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GrpcClientTemplate<REQ, RESP> {
    private static final Logger logger = Logger.getLogger(GrpcClientTemplate.class.getName());
    private static final int CHANNEL_SHUTDOWN_TIMEOUT = 5; // 通道关闭超时时间（秒）
    private final String target; // channel name（目标服务地址）

    public GrpcClientTemplate(String target) {
        this.target = target;
    }

    protected abstract Object createBlockingStub(Channel channel);

    protected abstract RESP doRpcCall(Object stub, REQ request);

    public <T> void execute(Function<T, REQ> requestBuilder,
                            Consumer<RESP> responseHandler,
                            T businessParam) {
        execute(requestBuilder, responseHandler, e -> logger.log(Level.WARNING, "gRPC调用失败", e), businessParam);
    }

    public <T> void execute(Function<T, REQ> requestBuilder,
                            Consumer<RESP> responseHandler,
                            Consumer<Throwable> exceptionHandler,
                            T businessParam) {
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();

        try {
            Object stub = createBlockingStub(channel);

            REQ request = requestBuilder.apply(businessParam);
            logger.info("gRPC请求构造完成，target=" + target);

            RESP response = doRpcCall(stub, request);

            responseHandler.accept(response);

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC调用失败，status={0}", e.getStatus());
            exceptionHandler.accept(e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "gRPC调用异常", e);
            exceptionHandler.accept(e);
        } finally {
            try {
                if (!channel.isShutdown()) {
                    channel.shutdownNow().awaitTermination(CHANNEL_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "通道关闭被中断", e);
            }
        }
    }
}