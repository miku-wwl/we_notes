nacos 重要配置： 必须在 bootstrap.yml 里面配置， 否则会导致动态刷新失败，@RefreshScope 也无效的问题。

```yml
spring:
  profiles:
    active: dev
  application:
    name: nacos-config-example
  cloud:
    nacos:
      config:
        serverAddr: 127.0.0.1:8848
        username: "nacos"
        password: "nacos"
        prefix: "hello world"
        file-extension: yaml
        namespace: xxxxxv
        group: xxxxxx
```

有用的 wiki
https://blog.csdn.net/a745233700/article/details/122916208

nacos 快速部署
https://nacos.io/zh-cn/docs/quick-start.html
