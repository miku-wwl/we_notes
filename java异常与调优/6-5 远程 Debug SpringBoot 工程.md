![alt text](image-57.png)

这张图片讲述了如何远程 Debug SpringBoot 工程。

1. **关于远程调试的说明**：远程调试协议支持 jdwp，IDEA 的远程调试需要满足两个前提条件：本地机器与部署机器之间的网络要互通，两端的代码要一致。
2. **增加远程调试的配置**：具体步骤如下：
   - 在 SpringBoot 工程的`application.properties`文件中加入以下配置：
     ```properties
     server.port=8080
     spring.profiles.active=dev
     ```
   - 启动 SpringBoot 工程时加上远程调试参数：
     ```bash
     java -jar xxx.jar --spring.profiles.active=dev --debug
     ```
   - 在 IDEA 中创建一个新的 Remote Run/Debug Configuration，设置 Host 为部署机器的 IP 地址，Port 为 8000（默认）。

总结起来，远程调试是调试代码的一种重要手段，可以帮助我们在不同的环境中进行调试。在实际应用中，需要注意网络环境和代码的一致性。
