package com.weilai.thrift.asyn.server;

import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloResponse;
import com.weilai.thrift.HelloService;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;


public class HelloServiceAsyncImpl implements HelloService.AsyncIface {

    @Override
    public void sayHello(HelloMessage request, AsyncMethodCallback<HelloResponse> resultHandler) throws TException {
        String message = request.getMessage();
        System.out.println("接收到请求: " + message);

        HelloResponse response = new HelloResponse();
        response.setMessage("Hello " + message);

        resultHandler.onComplete(response);
    }
}