package com.weilai.sofa;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.listener.ChannelListener;
import com.alipay.sofa.rpc.transport.AbstractChannel;

import java.util.Arrays;
import java.util.List;

public class SofaConsumer {

    private static class FetchChannelListener implements ChannelListener {

        private ConsumerConfig<FetchService> config;

        public FetchChannelListener(ConsumerConfig<FetchService> consumerConfig) {
            config = consumerConfig;
        }

        @Override
        public void onConnected(AbstractChannel channel) {

        }

        @Override
        public void onDisconnected(AbstractChannel channel) {

        }
    }

    public static void main(String[] args) {
        ConsumerConfig<FetchService> consumerConfig = new ConsumerConfig<FetchService>()
                .setInterfaceId(FetchService.class.getName())   //连接的接口，上下游通信标准
                .setProtocol("bolt")        //RPC通信的协议:bolt
                .setTimeout(5000)           //超时设置
                .setDirectUrl("bolt://127.0.0.1:4396");//直连地址
        //可增加多个连接监听器,但列表只放一个
        //consumerConfig.setOnConnect(Lists.newArrayList(new FetchChannelListener(consumerConfig)));
        consumerConfig.setOnConnect(Arrays.asList(new FetchChannelListener(consumerConfig)));

        List<String> str1 = consumerConfig.refer().fetchData();
        List<String> str2 = consumerConfig.refer().fetchData();

        System.out.println("str1:" + str1);
        System.out.println("str2:" + str2);
    }
}