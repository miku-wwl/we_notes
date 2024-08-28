tar -xzf apache-zookeeper-3.8.1-bin.tar.gz

cd apache-zookeeper-3.8.1-bin/conf/

cp zoo_sample.cfg zoo.cfg

vim zoo.cfg

修改 dataDir=/home/zookeeper/dataDir
修改启动端口 admin.serverPort=9091

启动zookeeper
./zkServer.sh start-foreground

启动zookeeper客户端

dubbo集成zookeeper

application.yml

dubbo:
  application:
    name: user-center
  registry:
    address: zookeeper://101.126.19.236:2181
  protocol:
    name: dubbo
    port: 20880
