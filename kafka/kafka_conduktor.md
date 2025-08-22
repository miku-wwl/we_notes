# Kafka 命令行操作完全指南

## 简介
本指南详细介绍 Kafka 命令行工具的核心操作，包括主题（Topic）管理、消息生产（Produce）和消息消费（Consume）等关键流程。所有操作基于 Kafka 命令行工具（CLI），适用于通过 Docker 部署的 Kafka 环境（推荐参考 [官方Docker启动指南](https://learn.conduktor.io/kafka/how-to-start-kafka-using-docker/) 完成前期部署）。


## 前提条件
1. 已通过 Docker 成功部署 Kafka 环境（包括 ZooKeeper 和 Kafka Broker）
2. 确保 Kafka 容器正常运行，且本地可访问 Kafka Broker 地址（默认 `localhost:9092`）
3. 进入 Kafka 容器终端或确保本地环境已配置 Kafka 命令行工具路径


## 一、Kafka 命令行工具版本验证
### 用途
检查当前使用的 Kafka 命令行工具版本，确保与 Kafka 集群版本兼容。

### 命令
```bash
kafka-topics --version
```

### 输出示例
```
2.8.1 (Commit:839b886f9b732b151a05746d7013643ee13f97c)
```

### 说明
- 版本号格式通常为 `X.Y.Z`，需与 Kafka Broker 版本保持兼容（建议主版本号一致）


## 二、Kafka 主题（Topic）管理

### 1. 创建主题
#### 用途
创建一个新的 Kafka 主题，指定分区数和副本因子。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
```

#### 参数解释
- `--bootstrap-server localhost:9092`：指定 Kafka Broker 地址（必填）
- `--topic first_topic`：指定主题名称（自定义，需唯一）
- `--create`：创建主题的操作标识
- `--partitions 3`：设置主题的分区数（3个分区，提高并行处理能力）
- `--replication-factor 1`：设置副本因子（1表示无冗余，生产环境建议≥2）

#### 成功输出
```
Created topic first_topic.
```

#### 注意事项
- 副本因子不能大于 Kafka 集群的 Broker 数量（单机 Docker 环境通常为1个 Broker，故副本因子只能为1）
- 主题名称需符合命名规范（字母、数字、下划线、连字符等，避免特殊字符）


### 2. 列出所有主题
#### 用途
查看当前 Kafka 集群中所有已创建的主题。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

#### 输出示例
```
first_topic
test_topic
```

#### 说明
- 输出结果为集群中所有主题的名称，按字母顺序排列


### 3. 查看主题详情
#### 用途
查看指定主题的详细配置信息，包括分区数、副本分布、首领分区等。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --describe --topic first_topic
```

#### 输出示例
```
Topic: first_topic	TopicId: abc123...	PartitionCount: 3	ReplicationFactor: 1	Configs: 
	Topic: first_topic	Partition: 0	Leader: 1	Replicas: 1	Isr: 1
	Topic: first_topic	Partition: 1	Leader: 1	Replicas: 1	Isr: 1
	Topic: first_topic	Partition: 2	Leader: 1	Replicas: 1	Isr: 1
```

#### 字段解释
- `PartitionCount`：主题的总分区数
- `ReplicationFactor`：副本因子
- `Partition`：分区编号（从0开始）
- `Leader`：负责处理该分区读写请求的 Broker ID
- `Replicas`：该分区的所有副本所在的 Broker ID 列表
- `Isr`：同步副本列表（与 Leader 保持数据同步的副本）


### 4. 修改主题分区数
#### 用途
增加主题的分区数量（Kafka 不支持减少分区数）。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --alter --topic first_topic --partitions 5
```

#### 参数解释
- `--alter`：修改主题配置的操作标识
- `--partitions 5`：将分区数从3增加到5

#### 成功输出
```
Updated partition count for topic "first_topic" to 5.
```

#### 注意事项
- 分区数只能增加，不能减少（若需减少需删除主题后重建）
- 新增分区不会影响已有分区的数据，但会影响消息的分区分配策略


### 5. 删除主题
#### 用途
删除指定的 Kafka 主题（需确认集群配置允许删除）。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --delete --topic first_topic
```

#### 输出示例
```
Topic first_topic is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.
```

#### 注意事项
- 需确保 Kafka 集群配置 `delete.topic.enable=true`（默认开启），否则仅标记为删除，不会实际删除
- 删除主题会永久删除所有数据，操作前需谨慎确认
- 若删除后需重建同名主题，建议等待片刻（确保删除完成），避免残留元数据冲突


### 6. 重新创建主题（补充）
#### 用途
在主题被删除后，重新创建同名主题（参数可与原主题不同）。

#### 命令
```bash
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
```

#### 说明
- 若主题已存在，会提示错误 `Topic 'first_topic' already exists`
- 重新创建的主题是全新的，不包含任何历史数据


## 三、通过 CLI 生产消息到 Kafka 主题

### 1. 基础消息生产
#### 用途
通过命令行终端手动输入消息，并发送到指定主题。

#### 命令
```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic
```

#### 操作流程
1. 执行命令后进入交互模式，终端显示 `>` 提示符
2. 输入消息内容（如 `Hello Kafka!`），按回车发送
3. 可连续输入多条消息，每条消息占一行
4. 按 `Ctrl+C` 退出生产者

#### 示例
```
> Hello Kafka!
> This is a test message.
> ^C  # 退出
```

#### 说明
- 消息默认无键（key），Kafka 会按轮询策略分配到主题的不同分区
- 消息发送成功后无返回提示，需通过消费者验证


### 2. 从文件读取消息并生产
#### 用途
批量发送文件中的内容到指定主题（每行内容作为一条消息）。

#### 命令
```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic < topic-input.txt
```

#### 参数解释
- `< topic-input.txt`：通过重定向符将文件内容作为生产者的输入

#### 说明
- `topic-input.txt` 为本地文本文件，需确保路径正确
- 文件中每行内容会被作为一条独立消息发送
- 适用于批量导入测试数据或历史数据


### 3. 生产带键（Key）的消息
#### 用途
生产包含键（Key）的消息，Kafka 会根据键的哈希值分配到固定分区（确保相同键的消息进入同一分区）。

#### 命令
```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:
```

#### 参数解释
- `--property parse.key=true`：启用键解析（默认不解析键）
- `--property key.separator=:`：指定键与值的分隔符（此处为冒号 `:`）

#### 操作流程
1. 执行命令后进入交互模式，输入格式为 `key:value`（如 `user1:Hello`）
2. 按回车发送，键为 `user1`，值为 `Hello`
3. 相同键（如 `user1`）的消息会被分配到同一分区

#### 示例
```
> user1:Hello from user1
> user2:Hello from user2
> user1:Another message from user1
> ^C  # 退出
```

#### 说明
- 若输入格式不符合 `key:value`（如缺少分隔符），键会被设为 `null`
- 键的存在不影响消息内容，仅影响分区分配策略


## 四、通过 CLI 从 Kafka 主题消费消息

### 1. 基础消息消费
#### 用途
实时消费指定主题中新增的消息（从当前时刻开始）。

#### 命令
```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic
```

#### 输出示例
```
Hello Kafka!
This is a test message.
```

#### 说明
- 消费者默认从最新消息开始消费（执行命令后生产的消息）
- 按 `Ctrl+C` 退出消费者
- 若主题无新消息，消费者会一直等待（阻塞状态）


### 2. 从头消费所有消息
#### 用途
消费主题中所有历史消息（包括创建以来的所有消息）。

#### 命令
```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning
```

#### 参数解释
- `--from-beginning`：指定从主题的最早消息开始消费

#### 输出示例
```
Hello Kafka!
This is a test message.
user1:Hello from user1
user2:Hello from user2
user1:Another message from user1
```

#### 说明
- 适用于查看主题中的所有历史数据
- 若消息量大，会持续输出直到所有消息消费完毕，之后转为等待新消息
- 多次执行该命令会重复消费所有消息（消费者默认不记录消费位置）


## 总结
本指南涵盖了 Kafka 命令行工具的核心操作，包括主题管理、消息生产和消费的完整流程。通过这些命令，可快速验证 Kafka 集群可用性、测试消息流、管理主题配置等。实际生产环境中，建议结合 Kafka 监控工具（如 Conduktor）和自动化脚本使用，提高操作效率。




















Simple check:

kafka安装 https://learn.conduktor.io/kafka/how-to-start-kafka-using-docker/

kafka-topics --version

kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1


kafka-topics --bootstrap-server localhost:9092 --list


kafka-topics --bootstrap-server localhost:9092 --describe --topic first_topic


kafka-topics --bootstrap-server localhost:9092 --alter --topic first_topic --partitions 5

kafka-topics --bootstrap-server localhost:9092 --delete --topic first_topic



kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1




---How to Produce a Message into a Kafka Topic using the CLI?---



kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic

kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic

kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic < topic-input.txt

kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:


---How to Consume Data in a Kafka Topic using the CLI?---


kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic


kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning



