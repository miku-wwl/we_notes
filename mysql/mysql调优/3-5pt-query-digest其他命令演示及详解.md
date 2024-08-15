#### 2024年8月15日 11:37

**从慢查询日志中分析索引使用情况**
命令：
```
pt-index-usage --user=root --password=123456 --host=localhost /var/lib/mysql/myshop02-slow.log
```

**从慢查询数据库表中找出重复的索引**
命令:
```
pt-duplicate-key-checker --host=localhost --user=root --password=123456
```

**查看MySQL表和文件的当前活动IO开销（不要在高峰时段使用）**
命令：
```
pt-ioprofile
```

**查看不同MySQL配置文件的差异（集群常用，双方都生效的变量）**
命令:
```
pt-config-diff /etc/my.cnf /root/my_master.cnf
```

**查找MySQL表和执行命令，示例如下：**
- 查找数据库里大于1MB的表
  ```
  pt-find --user=root --password=123456 --tablesize +1M
  ```
- 查看表和索引大小并按大小排序
  ```
  pt-find --user=root --password=123456 --printf '%T\t%D.%N\n' | sort -rn
  ```

**杀死符合标准的MySQL进程，示例如下：**
- 显示查询时间大于3秒的查询
  ```
  pt-kill --user=root --password=123456 --busy-time 3 --print
  ```
- 杀死查询时间大于3秒的查询
  ```
  pt-kill --user=root --password=123456 --busy-time 3 --kill
  ```

**查看MySQL授权（集群常用，授权复制）**
命令:
```
pt-show-grants --user=root --password=123456
```

**验证数据库复制的完整性（集群常用，主从复制后检验）**
命令:
```
pt-table-checksum --user=root --password=123456
```

**分析慢查询日志（重点）**
命令:
```
pt-query-digest /var/lib/mysql/myshop02-slow.log
```

### 示例与解释

#### 使用 sakila 数据库

假设我们有一个 sakila 数据库，我们将使用 `pt-query-digest` 来分析慢查询日志。

**命令示例:**
```
pt-query-digest /var/lib/mysql/sakila-slow.log
```

**解析:**

- **`pt-query-digest`**: 这个工具用于分析慢查询日志，生成统计报告，帮助理解哪些查询是慢查询，以及它们为何慢。
- **`/var/lib/mysql/sakila-slow.log`**: 这是慢查询日志文件的位置，假设我们在 sakila 数据库上启用了慢查询日志功能。

**输出示例:**
```
Digest: SELECT COUNT(*) FROM film WHERE film_id = ?
Samples: 200
Query time: 99.50s (avg: 497.5ms)
Lock time: 0.00s (avg: 0.00ms)
Rows sent: 200 (avg: 1)
Rows examined: 200 (avg: 1)
```

**解释:**
- **Digest**: 这是一个查询的摘要，表示该查询的基本形式。
- **Samples**: 表示该查询摘要对应的样本数量。
- **Query time**: 查询总耗时，括号内为平均查询时间。
- **Lock time**: 锁定总耗时，括号内为平均锁定时间。
- **Rows sent**: 返回的行数总数，括号内为平均值。
- **Rows examined**: 被检查的行数总数，括号内为平均值。

通过上述输出，我们可以看出这个查询虽然执行了多次，但是每次只返回一行数据，而且只检查了一行数据。如果这个查询很慢，可能是因为它被频繁执行或者因为其他原因导致查询时间较长。进一步的优化可能需要查看索引是否适当、是否有更高效的查询方式等。

如果您有任何具体的问题或需要更深入的分析，请告诉我！