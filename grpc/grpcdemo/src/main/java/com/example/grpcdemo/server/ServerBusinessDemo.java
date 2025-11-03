package com.example.grpcdemo.server;


import java.io.IOException;


public class ServerBusinessDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldGrpcServer greeterService = new HelloWorldGrpcServer();
        GrpcServerTemplate serverWrapper = new GrpcServerTemplate(greeterService);
        serverWrapper.start();
        serverWrapper.blockUntilShutdown();
    }
}
