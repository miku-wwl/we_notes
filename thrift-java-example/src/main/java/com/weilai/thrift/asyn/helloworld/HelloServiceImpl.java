package com.weilai.thrift.asyn.helloworld;


import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloResponse;
import com.weilai.thrift.HelloService;
import org.apache.thrift.TException;

public class HelloServiceImpl implements HelloService.Iface {

    @Override
    public HelloResponse sayHello(HelloMessage request) throws TException {
        String message = request.getMessage();
        System.out.println("接收到请求: {}"+ message);

        HelloResponse response = new HelloResponse();
        response.setMessage("Hello " + message);
        return response;
    }
}