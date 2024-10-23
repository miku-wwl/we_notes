package com.weilai.socket;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxSocketClient {
    private String ip = "127.0.0.1";
    private int port = 8091;
    private volatile NetSocket socket;
    private Vertx vertx = Vertx.vertx();
    private boolean isConnected = false;

    public void startup() {

        log.info("vertx has been loaded: {}", vertx);
        connectToServer();

        // 线程轮询缓存，缓存中有数据便发送
        new Thread(() -> {
            int i = 0;
            log.info("query thread start");
            while (true) {
                try {
                    Thread.sleep(1000);

                    if (!isConnected) {
                        continue; // 如果未连接，则跳过发送逻辑
                    }

                    Buffer buffer = MsgCode.encodeToBuffer(new CommonMsg(i++));

                    if (buffer != null && buffer.length() > 0 && socket != null) {
                        socket.write(buffer);
                        log.info("msg has been sent to gateway!");
                    }
                } catch (Exception e) {
                    log.error("msg send fail, continue");
                }
            }
        }).start();
    }

    private void connectToServer() {
        vertx.createNetClient().connect(port, ip, new ClientConnHandler());
    }

    public static void main(String[] args) {
        VertxSocketClient vertxSocketClient = new VertxSocketClient();
        vertxSocketClient.startup();
    }

    private class ClientConnHandler implements Handler<AsyncResult<NetSocket>> {
        @Override
        public void handle(AsyncResult<NetSocket> result) {
            if (result.succeeded()) {
                log.info("connect success to remote {}: {}", ip, port);
                socket = result.result();
                isConnected = true;
                // 关闭处理器
                socket.closeHandler(close -> {
                    log.info("connect to remote {} closed", socket.remoteAddress());
                    isConnected = false;
                    // 重连
                    reconnect();
                });
            } else {
                log.info("connect failed, please check!");
                reconnect();
            }
        }

        private void reconnect() {
            vertx.setTimer(5000, r -> {
                log.info("try reconnect to server at {}: {}", ip, port);
                connectToServer(); // 尝试重新连接
            });
        }
    }
}