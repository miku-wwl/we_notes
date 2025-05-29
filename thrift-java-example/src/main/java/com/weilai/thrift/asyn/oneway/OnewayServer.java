package com.weilai.thrift.asyn.oneway;

import com.weilai.thrift.HelloService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class OnewayServer {


    public static void main(String[] args) throws TTransportException {

        HelloServiceImpl helloService = new HelloServiceImpl();
        HelloService.Processor<HelloService.Iface> helloServiceProcessor = new HelloService.Processor<>(helloService);

        TServerTransport serverTransport = new TServerSocket(9090);

        TServer.Args serverArgs = new TServer.Args(serverTransport)
                .processor(helloServiceProcessor);

        TServer server = new TSimpleServer(serverArgs);
        server.serve();
    }
}