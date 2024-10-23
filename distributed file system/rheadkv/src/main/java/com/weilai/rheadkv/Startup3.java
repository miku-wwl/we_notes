package com.weilai.rheadkv;

import java.io.IOException;

public class Startup3 {

    public static void main(String[] args) throws IOException {
        String configName = "raft3.properties";
        NodeConfig nodeConfig = new NodeConfig(configName);
        nodeConfig.startup();
    }
}