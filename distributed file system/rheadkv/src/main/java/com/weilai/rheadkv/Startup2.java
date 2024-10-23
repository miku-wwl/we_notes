package com.weilai.rheadkv;

import java.io.IOException;

public class Startup2 {

    public static void main(String[] args) throws IOException {
        String configName = "raft2.properties";
        NodeConfig nodeConfig = new NodeConfig(configName);
        nodeConfig.startup();
    }
}
