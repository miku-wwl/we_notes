### pt-query-digest 安装及常用命令解析

#### 2024年8月15日
##### 10:42

**pt-query-digest**
- **简介**: `pt-query-digest` 是一个用于分析 MySQL 慢查询的第三方工具。它可以分析 binlog、General log、slow log，甚至可以通过 `SHOW PROCESSLIST` 或者通过 `tcpdump` 抓取的 MySQL 协议数据来进行分析。该工具能够将分析结果输出到文件中。分析过程包括对查询语句的条件进行参数化，之后对参数化后的查询进行分组统计，统计出各个查询的执行时间、次数、占比等信息。这有助于借助分析结果找出问题并进行优化。

**安装 Perl 模块**
- `pt-query-digest` 本质上是一个 Perl 脚本，因此需要先安装必要的 Perl 模块：
  ```bash
  yum install -y perl-CPAN perl-Time-HiRes
  ```

**下载 Percona Toolkit**
- 访问 Percona 官方网站下载页面:
  - [Percona Toolkit 下载](https://www.percona.com/downloads/percona-toolkit/)
  

**快速安装**
- 使用 wget 和 yum 安装 Percona Toolkit (以 CentOS 7 为例):
  ```bash
  wget https://www.percona.com/downloads/percona-toolkit/3.2.0/binary/redhat/7/x86_64/percona-toolkit-3.2.0-1.el7.x86_64.rpm
  yum localinstall -y percona-toolkit-3.2.0-1.el7.x86_64.rpm
  ```

---

### 使用示例

假设我们正在使用 `sakila` 数据库，我们可以用 `pt-query-digest` 分析慢查询日志来找出性能瓶颈。

**配置慢查询日志**
- 首先确保慢查询日志已启用。可以在 MySQL 配置文件（例如 `/etc/my.cnf`）中设置如下参数：
  ```ini
  [mysqld]
  slow_query_log = 1
  slow_query_log_file = /var/log/mysql/slow.log
  long_query_time = 2
  log_queries_not_using_indexes = 1
  ```

**运行 `pt-query-digest`**
- 假设慢查询日志文件为 `/var/log/mysql/slow.log`，我们可以运行以下命令：
  ```bash
  pt-query-digest --user=root --password=123456 --socket=/var/lib/mysql/mysql.sock /var/log/mysql/slow.log
  ```
- 注意: 使用明文密码在命令行中传递可能存在安全风险。考虑使用更安全的方法，如使用密钥文件。

**输出示例**
- 运行上述命令后，`pt-query-digest` 将输出一个报告，其中包括查询的时间、次数、平均执行时间、执行频率等信息。例如：
  ```plaintext
  Total: 100 queries, 100.000 sec, 294.292 qps, 29.429 rows per query, 100.00% full scans, 0.00% rows sent from storage engine, 100.00% rows examined, 0.00% rows using index, 100.00% rows using temporary tables, 100.00% rows using filesort.
  ```

**解读报告**
- 上述输出显示了总查询数、总执行时间、每秒查询数等指标。根据这些信息，我们可以进一步分析哪些查询占用了较多资源或执行时间较长，从而确定优化的方向。

**其他命令**
- `pt-summary`: 用于显示 MySQL 进程列表的摘要信息。
- `pt-diskstats`: 显示 MySQL 表的磁盘使用情况。

如果您需要更具体的示例或有其他疑问，请随时告诉我！