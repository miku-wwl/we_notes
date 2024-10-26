dubbo 是 rpc 框架,对标 openfeign

关键依赖
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>3.0.13</version>

<!-- dubbo -->

        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>3.2.3</version>
        </dependency>

<!-- nacos -->

         <dependency>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
            <version>2.2.0</version>
        </dependency>

<!-- zookeeper -->

          <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-x-discovery</artifactId>
            <version>5.5.0</version>
        </dependency>

provider 配置:

spring:
  application:
    name: dubbo-provider

dubbo:
  application:
    name: dubbo-provider
  registry:
#    address: zookeeper://101.126.19.236:2181
    address: nacos://101.126.19.236:8848
    timeout: 50000
    parameters:
      blockUntilConnectedWait:
        50
  protocol:
    name: dubbo
    port: 20880


consumer 配置:
spring:
  application:
    name: dubbo-provider


server:
  port: 8081

dubbo:
  application:
    name: dubbo-provider
  registry:
#    address: zookeeper://101.126.19.236:2181
    address: nacos://101.126.19.236:8848
    timeout: 50000
    parameters:
      blockUntilConnectedWait:
        50
  protocol:
    name: dubbo
    port: 20881

源代码见learnDubbo