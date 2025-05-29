package com.weilai.thrift.asyn.server;

import com.weilai.thrift.HelloService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

public class AsyncServer {

    public static void main(String[] args) throws TTransportException {

        HelloServiceAsyncImpl helloService = new HelloServiceAsyncImpl();
        HelloService.AsyncProcessor<HelloService.AsyncIface> helloServiceProcessor = new HelloService.AsyncProcessor<>(helloService);
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