tar -xzf apache-zookeeper-3.8.1-bin.tar.gz

cd apache-zookeeper-3.8.1-bin/conf/

cp zoo_sample.cfg zoo.cfg

vim zoo.cfg

修改 dataDir=/home/zookeeper/dataDir
修改启动端口 admin.serverPort=9091

启动 zookeeper
./zkServer.sh start-foreground

启动 zookeeper 客户端

dubbo 集成 zookeeper

application.yml

dubbo:
application:
name: user-center
registry:
address: zookeeper://101.126.19.236:2181
protocol:
name: dubbo
port: 20880

一些指令
ls -R /
ls -R /dubbo
get /dubbo/com.imooc.user.provider.service.UserCenterUserService

dubbo 注册 zookeeper 问题 wiki
https://blog.csdn.net/qq_42971035/article/details/129599758
https://blog.csdn.net/qq_41426990/article/details/125830495

后台运行 zookeeper
nohup ./zkServer.sh start-foreground &
