package com.example.grpcdemo.client;

import com.example.grpcdemo.helloworld.GreeterGrpc;
import com.example.grpcdemo.helloworld.HelloReply;
import com.example.grpcdemo.helloworld.HelloRequest;
import io.grpc.Channel;
import java.util.function.Consumer;
import java.util.function.Function;

public class HelloWorldGrpcClient {
    private final GrpcClientTemplate<HelloRequest, HelloReply> sayHelloClient;
    private final GrpcClientTemplate<HelloRequest, HelloReply> sayHello2Client;

    public HelloWorldGrpcClient(String target) {
        this.sayHelloClient = new GrpcClientTemplate<HelloRequest, HelloReply>(target) {
            @Override
            protected Object createBlockingStub(Channel channel) {
                return GreeterGrpc.newBlockingStub(channel); // 共用Greeter Stub
            }

            @Override
            protected HelloReply doRpcCall(Object stub, HelloRequest request) {
                GreeterGrpc.GreeterBlockingStub greeterStub = (GreeterGrpc.GreeterBlockingStub) stub;
                return greeterStub.sayHello(request); // 绑定SayHello方法
            }
        };

        this.sayHello2Client = new GrpcClientTemplate<HelloRequest, HelloReply>(target) {
            @Override
            protected Object createBlockingStub(Channel channel) {
                return GreeterGrpc.newBlockingStub(channel); // 共用Greeter Stub
            }

            @Override
            protected HelloReply doRpcCall(Object stub, HelloRequest request) {
                GreeterGrpc.GreeterBlockingStub greeterStub = (GreeterGrpc.GreeterBlockingStub) stub;
                return greeterStub.sayHello2(request); // 绑定SayHello2方法
            }
        };
    }

    public <T> void executeSayHello(Function<T, HelloRequest> requestBuilder,
                                    Consumer<HelloReply> responseHandler,
                                    T businessParam) {
        sayHelloClient.execute(requestBuilder, responseHandler, businessParam);
    }

    public <T> void executeSayHello(Function<T, HelloRequest> requestBuilder,
                                    Consumer<HelloReply> responseHandler,
                                    Consumer<Throwable> exceptionHandler,
                                    T businessParam) {
        sayHelloClient.execute(requestBuilder, responseHandler, exceptionHandler, businessParam);
    }

    public <T> void executeSayHello2(Function<T, HelloRequest> requestBuilder,
                                     Consumer<HelloReply> responseHandler,
                                     T businessParam) {
        sayHello2Client.execute(requestBuilder, responseHandler, businessParam);
    }

    public <T> void executeSayHello2(Function<T, HelloRequest> requestBuilder,
                                     Consumer<HelloReply> responseHandler,
                                     Consumer<Throwable> exceptionHandler,
                                     T businessParam) {
        sayHello2Client.execute(requestBuilder, responseHandler, exceptionHandler, businessParam);
    }
}