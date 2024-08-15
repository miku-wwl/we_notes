### pt-query-digest 常用命令详解

#### 分析慢查询日志

**命令:**
```sh
pt-query-digest /var/lib/mysql/myshop02-slow.log
```

**解析:**
- `pt-query-digest`: 这是一个用于分析慢查询日志的工具，它可以统计慢查询日志中的 SQL 查询，并按执行次数、总执行时间等进行排序。
- `/var/lib/mysql/myshop02-slow.log`: 指定慢查询日志文件的位置。

**改进后的命令:**
如果你希望看到所有查询（100%），可以使用 `--limit` 参数：
```sh
pt-query-digest --limit=100% /var/lib/mysql/myshop02-slow.log
```

#### 查找 MySQL 的从库和同步状态

**命令:**
```sh
pt-slave-find --host=localhost --user=root --password=123456
```

**解析:**
- `pt-slave-find`: 这个工具用来检测 MySQL 主从复制的状态。
- `--host=localhost`: 指定 MySQL 服务器的主机名或 IP 地址。
- `--user=root`: 指定登录 MySQL 的用户名。
- `--password=123456`: 指定登录 MySQL 的密码。

**注意:** 不要在公开场合直接写明密码，可以考虑使用更安全的方式如环境变量传递。

#### 查看 MySQL 的死锁信息

**命令:**
```sh
pt-deadlock-logger --run-time=10 --interval=3 --create-dest-table --dest D=test,t=deadlocks --user=root --password=123456
```

**解析:**
- `pt-deadlock-logger`: 用于记录 MySQL 中发生的死锁事件。
- `--run-time=10`: 死锁记录器运行的时间长度为 10 秒。
- `--interval=3`: 检查死锁的时间间隔为 3 秒。
- `--create-dest-table`: 创建一个目标表来存储死锁记录。
- `--dest D=test,t=deadlocks`: 指定目标数据库为 `test`，表名为 `deadlocks`。
- `--user=root`: 指定登录 MySQL 的用户名。
- `--password=123456`: 指定登录 MySQL 的密码。

**注意:** 同样地，不要在脚本中硬编码密码。

#### 构造一个死锁的场景

**MySQL 命令:**
```sql
mysql> set autocommit=0;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from t1 where id=1 for update;
+----+-------+
| id | name  |
+----+-------+
|  1 | smart |
+----+-------+
1 row in set (0.00 sec)

mysql> set autocommit=0;
Query OK, 0 rows affected (0.01 sec)

mysql> select * from t2 where id=1 for update;
+----+--------+
| id | name   |
+----+--------+
|  1 | anl    |
+----+--------+
1 row in set (0.16 sec)

mysql> select * from t1 where id=1 for update;
```

**解析:**
这里试图创建一个死锁的情况，其中两个会话分别锁定不同的表上的相同行 (`t1` 和 `t2`)，然后尝试锁定另一个会话已经锁定的行。

**注意:**
- 确保 `t1` 和 `t2` 表存在，并且包含至少一行数据。
- 在实际环境中，应该避免这样的死锁情况发生，可以通过合理的事务隔离级别或者加锁顺序来预防。

以上是您的笔记整理和一些额外的说明。如果您需要进一步的帮助，请告诉我！