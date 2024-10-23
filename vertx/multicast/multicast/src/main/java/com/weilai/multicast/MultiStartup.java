package com.weilai.multicast;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MultiStartup {
    public static void main(String[] args) {
        String multicastIp = "239.0.0.1";
        int multicastPort = 1234;
        MultiBodyCodec multiBodyCodec = new MultiBodyCodec();

        DatagramSocket multicastSender = Vertx.vertx().createDatagramSocket(new DatagramSocketOptions());

        long packNo = 0;

        while (true) {
            try {
                List<String> strs = Arrays.asList("Str1", "str2", "str3", "str4", String.valueOf(packNo));
                MultiStrsPack strsPack = new MultiStrsPack(packNo, strs);
                log.info(strsPack.toString());

                //生成PackNo并打包成Cmd
                //入库,将类使用Hessian2转换为字节流并存到KVStore里
                byte[] serialize = multiBodyCodec.serialize(strsPack);
                multicastSender.send(
                        Buffer.buffer(serialize),    //vertx包装好的Buffer类
                        multicastPort,
                        multicastIp,
                        null    //不配置异步处理器
                );
                //更新PackNo++
                packNo++;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}