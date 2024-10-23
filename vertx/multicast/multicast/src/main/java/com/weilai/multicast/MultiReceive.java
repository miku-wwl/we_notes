package com.weilai.multicast;

import com.alipay.remoting.exception.CodecException;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Slf4j
public class MultiReceive {
    private static Vertx vertx = Vertx.vertx();

    public static void main(String[] args) {
        String multicastIp = "239.0.0.1";
        int multicastPort = 1234;
        MultiBodyCodec multiBodyCodec = new MultiBodyCodec();
        //接收数据(组播)
        DatagramSocket datagramSocket = vertx.createDatagramSocket(new DatagramSocketOptions());

        datagramSocket.listen(multicastPort, "0.0.0.0", asyncRes -> {
            if (asyncRes.succeeded()) {
                log.info("UDP listen succeed!");
                //处理接收到的数据
                datagramSocket.handler(packet -> {
                    Buffer udpData = packet.data();
                    if (udpData.length() > 0) {
                        try {
                            MultiStrsPack strsPack = multiBodyCodec.deserialize(udpData.getBytes(), MultiStrsPack.class);
                            log.info("strsPack is {}", strsPack);
                        } catch (CodecException e) {
                            log.error("decode packet error", e);
                        }
                    } else {
                        //接收到空数据的包
                        log.error("recv empty udp packet from client: {}", packet.sender().toString());
                    }
                });
                try {
                    //组播
                    datagramSocket.listenMulticastGroup(
                            multicastIp,        //排队机地址
//                            "0.0.0.0",
                            mainInterface().getName(),   //需要监听的网卡名字
                            null,           //监听的地址(不需要)
                            asyncRes2 -> {  //处理器进行判断，看是否加入组播
                                log.info("UDP listen from {}: {} succeed? {}", multicastIp, multicastPort, asyncRes2.succeeded());
                            }
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //打印监听失败的错误日志
                log.error("Listen failed, ", asyncRes.cause());
            }
        });
    }

    private static NetworkInterface mainInterface() throws Exception {
        final ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        NetworkInterface networkInterface = interfaces.stream().filter(t -> {
            try {
                boolean isLoopback = t.isLoopback();
                boolean supportsMulticast = t.supportsMulticast();
                boolean isVirtualBox = t.getDisplayName().contains("VirtualBox") || t.getDisplayName().contains("Host-only");
                boolean hasIpv4 = t.getInterfaceAddresses().stream().anyMatch(ia -> ia.getAddress() instanceof Inet4Address);
                return !isLoopback && supportsMulticast && !isVirtualBox && hasIpv4;
            } catch (Exception e) {
                log.error("find net interface error", e);
            }
            return false;
        }).sorted(Comparator.comparing(NetworkInterface::getName)).findFirst().orElse(null);  //找到第一个符合条件的网卡，否则返回空
        if (networkInterface != null) {
            log.info("The Network Interface is {}", networkInterface.getName());
        } else {
            log.info("No Network Interface available");
        }
        return networkInterface;
    }
}
