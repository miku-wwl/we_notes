研究真实场景、移除 `.withFake(true)` 以及StorageType.RocksDB 和 StorageType.Memory区别

```java
public class RaftClusterStarter {

    public static void main(String[] args) {
        // 假设这是集群中三个节点的地址
        String serverList = "192.168.1.10:8891,192.168.1.11:8892,192.168.1.12:8893";
        
        // 当前节点的服务地址
        String serveUrl = "192.168.1.10:8891";
        String[] split = serveUrl.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);

        // 数据存储路径，假设每个节点的数据存放在各自的目录下
        String dataPath = "/var/raft/data/node1";

        // 对KV数据库存储引擎进行相关设置
        final StoreEngineOptions storeEngineOptions = StoreEngineOptionsConfigured.newConfigured()
                .withStorageType(StorageType.RocksDB)    // 使用RocksDB来存储Raft的数据
                .withRocksDBOptions(RocksDBOptionsConfigured.newConfigured().config())
                .withRaftDataPath(dataPath)
                .withServerAddress(new Endpoint(ip, port))
                .config();

        // 针对集群中多个store的配置
        final PlacementDriverOptions placementDriverOptions = PlacementDriverOptionsConfigured.newConfigured()
                .config(); // 使用默认配置

        // 将以上配置配置到KV储存选项中
        final RheaKVStoreOptions rheaKVStoreOptions = RheaKVStoreOptionsConfigured.newConfigured()
                .withInitialServerList(serverList)
                .withStoreEngineOptions(storeEngineOptions)
                .withPlacementDriverOptions(placementDriverOptions)
                .config();

        // 创建并启动节点
        Node node = new Node(rheaKVStoreOptions);
        node.start();
    }
}
```