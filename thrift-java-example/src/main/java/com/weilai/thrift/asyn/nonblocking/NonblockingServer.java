package com.weilai.thrift.asyn.nonblocking;

import com.weilai.thrift.HelloService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

public class NonblockingServer {

    public static void main(String[] args) throws TTransportException {

        HelloServiceImpl helloService = new HelloServiceImpl();
        HelloService.Processor<HelloService.Iface> helloServiceProcessor = new HelloService.Processor<>(helloService);

        TNonblockingServerTransport transport = new TNonblockingServerSocket(9090);

        // 配置参数以及处理器
        TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(transport)
                .selectorThreads(4)
                .workerThreads(10)
                .acceptQueueSizePerThread(20)
                .processor(helloServiceProcessor);

        TServer server = new TThreadedSelectorServer(serverArgs);

        server.serve();
    }
}