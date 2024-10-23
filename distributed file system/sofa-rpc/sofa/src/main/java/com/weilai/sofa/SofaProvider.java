package com.weilai.sofa;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;

import java.util.Arrays;

public class SofaProvider {
    public static void main(String[] args) {
        ServerConfig serverConfig = new ServerConfig()
                .setPort(4396)
                .setProtocol("bolt");

        ProviderConfig<FetchService> providerConfig = new ProviderConfig<FetchService>()
                .setInterfaceId(FetchService.class.getName())     //Provider指定接口的名字暴露给Consumer
                //指定FetchService实现类来实现响应(直接从单例的Container获取即可)，理论上应该传入FetchService的实现类
                .setRef(() -> Arrays.asList("111", "222", "333"))
                .setServer(serverConfig);

        providerConfig.export();      //发布服务
        System.out.println("SofaProvider start");
    }
}