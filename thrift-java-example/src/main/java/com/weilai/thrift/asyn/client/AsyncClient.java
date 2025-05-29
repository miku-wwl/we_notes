package com.weilai.thrift.asyn.client;

import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloResponse;
import com.weilai.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;

public class AsyncClient {

    public static void main(String[] args) throws InterruptedException {

        try {
            // 构建异步客户端
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            HelloService.AsyncClient.Factory factory = new HelloService.AsyncClient.Factory(clientManager, protocolFactory);

            TNonblockingTransport nonblockingTransport = new TNonblockingSocket("localhost", 9090);
            HelloService.AsyncClient asyncClient = factory.getAsyncClient(nonblockingTransport);

            // 异步回调
            AsyncMethodCallback<HelloResponse> callback = new AsyncMethodCallback<HelloResponse>() {
                @Override
                public void onComplete(HelloResponse response) {
                    System.out.println("响应结果: " + response.getMessage());
                }

                @Override
                public void onError(Exception exception) {
                    System.out.println("请求失败: " + (exception.getMessage() != null ? exception.getMessage() : "未知错误"));
                    exception.printStackTrace();
                }
            };

            // 构建请求
            HelloMessage request = new HelloMessage();
            request.setMessage("Async Thrift");

            // 调用
            asyncClient.sayHello(request, callback);

        } catch (TException | IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(3_000);
    }
}