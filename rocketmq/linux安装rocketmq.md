下载 rocketmq https://rocketmq.apache.org/download

unzip rocketmq-all-5.3.0-bin-release.zip

runbroker.sh、 runserver.sh 修改 jvm 内存参数

runbroker.sh 修改：JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m"
runserver.sh修改：JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"

修改 broker.conf， 在末尾添加:

autoCreateTopicEnable=true
namesrvAddr=117.72.69.172:9876
brokerIP1=117.72.69.172

参考文档启动 rocketmq https://rocketmq.apache.org/docs/quickStart/01quickstart/

关闭 rocketmq 可使用 jps, 结合 kill

rocketmq-spring-boot-starter 支持 springboot3 集成

参考代码可见 learnRocketmq 项目
