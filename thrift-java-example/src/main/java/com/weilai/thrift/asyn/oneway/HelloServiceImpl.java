package com.weilai.thrift.asyn.oneway;

import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloService;
import org.apache.thrift.TException;


public class HelloServiceImpl implements HelloService.Iface {

    @Override
    public void sayHello(HelloMessage request) throws TException {
        String message = request.getMessage();
        System.out.println("接收到请求: " + message);
    }
}