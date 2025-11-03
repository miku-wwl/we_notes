package com.example.grpcdemo.client;

import com.example.grpcdemo.helloworld.HelloRequest;

/**
 * 业务层使用示例：支持调用SayHello和SayHello2两个RPC方法
 */
public class BusinessDemo {
    public static void main(String[] args) {
        // 1. 配置服务地址（统一配置，无需修改）
        String target = "localhost:50051";

        // 2. 创建业务客户端（仅需初始化一次）
        HelloWorldGrpcClient grpcClient = new HelloWorldGrpcClient(target);

        // -------------------------- 原有：调用SayHello --------------------------
        String businessParam1 = "张三";
        grpcClient.executeSayHello(
                name -> HelloRequest.newBuilder().setName(name).build(), // 入参构造
                reply -> System.out.println("SayHello响应：" + reply.getMessage()), // 响应处理
                businessParam1
        );

        // 扩展：SayHello自定义异常处理
        grpcClient.executeSayHello(
                name -> HelloRequest.newBuilder().setName(name).build(),
                reply -> System.out.println("SayHello响应：" + reply.getMessage()),
                e -> System.err.println("SayHello异常：" + e.getMessage()), // 异常处理
                "李四"
        );

        // -------------------------- 新增：调用SayHello2 --------------------------
        String businessParam2 = "王五";
        grpcClient.executeSayHello2(
                name -> HelloRequest.newBuilder().setName(name).build(), // 入参构造（可复用或自定义）
                reply -> System.out.println("SayHello2响应：" + reply.getMessage()), // 响应处理
                businessParam2
        );

        // 扩展：SayHello2自定义异常处理
        grpcClient.executeSayHello2(
                name -> HelloRequest.newBuilder().setName(name).build(),
                reply -> System.out.println("SayHello2响应：" + reply.getMessage()),
                e -> System.err.println("SayHello2异常：" + e.getMessage()), // 异常处理
                "赵六"
        );
    }
}