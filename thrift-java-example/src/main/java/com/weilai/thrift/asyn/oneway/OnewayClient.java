package com.weilai.thrift.asyn.oneway;

import com.weilai.thrift.HelloMessage;
import com.weilai.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


public class OnewayClient {

    public static void main(String[] args) throws InterruptedException {

        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);

            HelloService.Client client = new HelloService.Client(protocol);

            HelloMessage request = new HelloMessage();
            request.setMessage("Oneway");

            client.sayHello(request);
            System.out.println("请求发送完成");
        } catch (TException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);

    }
}