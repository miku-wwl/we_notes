package com.weilai.socket;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxSocketServer {
    private String ip = "127.0.0.1";
    private int port = 8091;
    private volatile NetSocket socket;
    private Vertx vertx = Vertx.vertx();

    public void startup() {
        //启动TCP监听
        initRecv();
    }

    public void initRecv() {
        NetServer netServer = vertx.createNetServer();

        netServer.connectHandler(new ConnHandler());

        netServer.listen(port, res -> {
            if (res.succeeded()) {
                log.info("gateway startup at port: {}", port);
            } else {
                log.error("gateway startup fail");
            }
        });
    }

    public static void main(String[] args) {
        VertxSocketServer vertxSocketServer = new VertxSocketServer();
        vertxSocketServer.startup();
    }


    public class ConnHandler implements Handler<NetSocket> {

        //包头[ id int]
        private static final int PACKET_HEADER_LENGTH = 4;

        @Override
        public void handle(NetSocket netSocket) {

            final RecordParser parser = RecordParser.newFixed(PACKET_HEADER_LENGTH);
            //设置报文接收处理器
            parser.setOutput(new Handler<Buffer>() {
                int id = -1;

                @Override
                public void handle(Buffer buffer) {
                    //读取报头
                    id = buffer.getInt(0);  //4字节
                    CommonMsg commonMsg = new CommonMsg();
                    commonMsg.setId(id);
                    log.info(commonMsg.toString());
                }
            });

            netSocket.handler(parser);

            //异常 退出处理器
            netSocket.closeHandler(close -> {

            });
            netSocket.exceptionHandler(e -> {

            });
        }
    }

}