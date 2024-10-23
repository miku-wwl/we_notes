package com.weilai.rheadkv;

import com.alipay.sofa.jraft.rhea.options.PlacementDriverOptions;
import com.alipay.sofa.jraft.rhea.options.RheaKVStoreOptions;
import com.alipay.sofa.jraft.rhea.options.StoreEngineOptions;
import com.alipay.sofa.jraft.rhea.options.configured.MemoryDBOptionsConfigured;
import com.alipay.sofa.jraft.rhea.options.configured.PlacementDriverOptionsConfigured;
import com.alipay.sofa.jraft.rhea.options.configured.RheaKVStoreOptionsConfigured;
import com.alipay.sofa.jraft.rhea.options.configured.StoreEngineOptionsConfigured;
import com.alipay.sofa.jraft.rhea.storage.StorageType;
import com.alipay.sofa.jraft.util.Endpoint;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;

/**
 * 对KVStore节点的配置类
 * 读取properties里的属性作为配置作为Node的配置
 */
@Slf4j
@Data
@ToString
@RequiredArgsConstructor
public class NodeConfig {

    /**
     * 存放数据/日志的本地路径
     */
    private String dataPath;

    /**
     * 当前节点服务的地址
     */
    private String serveUrl;

    /**
     * KVStore集群有哪些地址
     */
    private String serverList;

    @NonNull
    private String fileName;

    private Node node;

    private BodyCodec bodyCodec = new BodyCodec();

    /**
     * 从配置文件中读取属性并赋值给此对象
     * 初始化KVStore并监听监听节点状态
     *
     * @throws IOException 读取properties文件流时的异常
     */
    public void startup() throws IOException {
        System.out.println("Hello world!");
        //读取配置文件
        initConfig();

        //初始化集群(Raft算法的排队机集群)
        startSeqDbCluster();

        startupFetch();
    }

    private void initConfig() throws IOException {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(inputStream);
        this.dataPath = properties.getProperty("datapath");
        this.serveUrl = properties.getProperty("serveurl");
        this.serverList = properties.getProperty("serverlist");
        System.out.println(this.dataPath);
        System.out.println(this.serveUrl);
        System.out.println(this.serverList);
        log.info("datapath is: {}", this.dataPath);
        log.info("serverurl is: {}", this.serveUrl);
        log.info("serverlist is: {}", this.serverList);
    }

    /**
     * 启动KVStore
     */
    private void startSeqDbCluster() {
        String[] split = serveUrl.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);

        //对KV数据库存储引擎进行相关设置
        final StoreEngineOptions storeEngineOptions = StoreEngineOptionsConfigured.newConfigured()
                .withStorageType(StorageType.Memory)    //使用memory来存储Raft的数据
                .withMemoryDBOptions(MemoryDBOptionsConfigured.newConfigured().config())
                .withRaftDataPath(dataPath)
                .withServerAddress(new Endpoint(ip, port))
                .config();

        //针对集群中多个store的配置，实际中只有一个集群，所以指定了Fake
        final PlacementDriverOptions placementDriverOptions = PlacementDriverOptionsConfigured.newConfigured()
                .withFake(true)
                .config();

        //将以上配置配置到KV储存选项中
        final RheaKVStoreOptions rheaKVStoreOptions = RheaKVStoreOptionsConfigured.newConfigured()
                .withInitialServerList(serverList)
                .withStoreEngineOptions(storeEngineOptions)
                .withPlacementDriverOptions(placementDriverOptions)
                .config();

        node = new Node(rheaKVStoreOptions);
        node.start();
        //将节点的stop方法挂载在jdk的shutdown流程中
        Runtime.getRuntime().addShutdownHook(new Thread(node::stop));
    }

    /**
     * 从网关中获得数据
     * 逻辑:
     * 1.从哪些网关抓取
     * 2.通信方式
     */
    private void startupFetch() {
        /*
          使用jdk的Timer进行从网关定时抓取数据的任务
          参数分别为要执行的线程，延迟执行的时间，执行的频率
         */
        new Timer().schedule(new FetchTask(this), 1000, 1000);
    }


}