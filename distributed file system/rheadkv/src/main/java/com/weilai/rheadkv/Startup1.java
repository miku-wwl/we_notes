package com.weilai.rheadkv;

import java.io.IOException;

public class Startup1 {

    public static void main(String[] args) throws IOException {
        String configName = "raft1.properties";
        NodeConfig nodeConfig = new NodeConfig(configName);
        nodeConfig.startup();
    }
}