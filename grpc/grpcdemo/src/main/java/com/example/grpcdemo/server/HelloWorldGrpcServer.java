package com.example.grpcdemo.server;

import com.example.grpcdemo.helloworld.GreeterGrpc;
import com.example.grpcdemo.helloworld.HelloReply;
import com.example.grpcdemo.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

/**
 * HelloWorld业务专用客户端：绑定Greeter服务，业务层直接使用
 */
public class HelloWorldGrpcServer extends GreeterGrpc.GreeterImplBase{
    private static final Logger logger = Logger.getLogger(ServerBusinessDemo.class.getName());
    // 服务实现类
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        logger.info("Processed request: name=" + req.getName() + ", response=" + reply.getMessage());
    }

    @Override
    public void sayHello2(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        logger.info("Processed request: name=" + req.getName() + ", response=" + reply.getMessage());
    }

}