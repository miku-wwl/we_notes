### 3-1 `mysqldumpslow` 用法详解及示例

#### 2024年8月15日
10:26

**mysqldumpslow**

**简介：**
如果启用了慢查询日志，就会生成大量数据。通过对这些日志的分析，我们可以生成分析报表，进而进行性能优化。

**命令帮助：**
```
mysqldumpslow --help
```

**纯文本查看慢查询日志文件：**
```
more /var/lib/mysql/myshop02-slow.log
```

**查看慢查询日志示例：**
```
mysqldumpslow --verbose /var/lib/mysql/myshop02-slow.log
```

**输出示例：**
```
count:1 Time=447.66s(447s) Lock=0.00s (0s) Rows=0.0 (0), root[root]@localhost
call pro_t1O

count:1 Time=76.55s (76s) Lock=0.00s (0s) Rows=9699779.0 (9699779), root[root]@localhost
select * from t1, t2 where t1.id != t2.id
```

**按不同标准排序慢查询：**
```
mysqldumpslow -s c /var/lib/mysql/myshop02-slow.log
mysqldumpslow -s t /var/lib/mysql/myshop02-slow.log
mysqldumpslow -s r /var/lib/mysql/myshop02-slow.log
mysqldumpslow -s at /var/lib/mysql/myshop02-slow.log
mysqldumpslow -s at -t 5 /var/lib/mysql/myshop02-slow.log
```

**工具特性：**
此工具通常随MySQL一同安装。尽管它提供的统计数据较少，例如不包括CPU和I/O的信息，但它仍然是最常用的工具之一。

---

### 具体案例说明

假设我们在`sakila`数据库上启用了慢查询日志，并想要找到执行时间最长的查询。我们可以使用如下命令查看执行时间最长的前5个查询：

```
mysqldumpslow -s t -t 5 /var/lib/mysql/sakila-slow.log
```

这里 `-s t` 表示按照查询时间（time）排序，`-t 5` 表示显示前5个查询。

让我们进一步假设慢查询日志中包含了如下的查询：

```
count:2 Time=34.56s (34s) Lock=0.00s (0s) Rows=500.0 (500), user@host
SELECT * FROM sakila.film JOIN sakila.inventory ON film.film_id = inventory.film_id WHERE film.title LIKE 'SOMETHING'
```

这个查询被执行了两次，每次执行时间大约为34秒，没有锁定时间，返回了大约500行数据。

针对这样的查询，我们可以采取以下优化措施：

1. **添加索引：** 如果`film.title`字段没有合适的索引，可以考虑为其创建一个全文索引。
2. **限制返回的数据量：** 可以通过添加`LIMIT`子句来减少返回的行数。
3. **选择性地使用字段：** 如果不需要所有的列，可以只选择需要的字段，而不是使用`SELECT *`。

这些步骤可以帮助我们提高查询性能，并减少慢查询的数量。

如果您有`sakila`数据库的具体查询案例，我们可以进一步讨论如何优化它们。
---