package com.weilai.thrift.asyn.server;

import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloResponse;
import com.weilai.thrift.HelloService;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFramedTransport;


public class BlockingClient {

    public static void main(String[] args) {

        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport = new TFramedTransport(transport);

            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);

            HelloService.Client client = new HelloService.Client(protocol);

            HelloMessage request = new HelloMessage();
            request.setMessage("Async Server");

            HelloResponse response = client.sayHello(request);
            System.out.println("返回响应: {}" + response.getMessage());
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}