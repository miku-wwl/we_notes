### 4-1 还原企业开发场景之 SQL 执行计划优化

#### 2024年8月15日 14:50

#### 通过 EXPLAIN 分析 SQL 执行计划
使用 `EXPLAIN` 查询 SQL 的执行计划。

SQL 的执行计划反映了 SQL 的执行效率，在执行的 SQL 前面加上 `EXPLAIN` 即可。

**示例：**
```sql
EXPLAIN SELECT * FROM actor;
```

| id   | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra |
| ---- | ----------- | ----- | ---------- | ---- | ------------- | ---- | ------- | ---- | ---- | -------- | ----- |
| 1    | SIMPLE      | actor | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 200  | 100.00   | NULL  |

#### 执行计划的字段解释与举例

**id 列**
- 数字越大越先执行。
- 如果数字一样大，则从上往下依次执行。
- `id` 列为 `NULL` 表示这是一个结果集，不需要使用它来进行查询。

**示例：**
```sql
EXPLAIN SELECT (SELECT 1 FROM actor LIMIT 1) FROM film;
```

| id   | select_type | table | partitions | type  | possible_keys | key                 | key_len | ref  | rows | filtered | Extra                                          |
| ---- | ----------- | ----- | ---------- | ----- | ------------- | ------------------- | ------- | ---- | ---- | -------- | ---------------------------------------------- |
| 1    | PRIMARY     | film  | NULL       | index | NULL          | idx_fk_language_id  | 1       | NULL | 1000 | 100.00   | Using index                                    |
| 2    | SUBQUERY    | actor | NULL       | index | NULL          | idx_actor_last_name | 182     | NULL | 200  | 100.00   | Using index; Rows_in_set, 1 warning (0.00 sec) |

**select_type 列**
- `SIMPLE`: 表示不需要 UNION 操作或不包含子查询的简单 SELECT 查询。
- `PRIMARY`: 一个需要 UNION 操作或含有子查询的 SELECT，位于最外层的查询。
- `UNION`: UNION 连接的两个 SELECT 查询，第一个查询是 derived 派生表，除了第一个表外，第二个以后的表 `select_type` 都是 `UNION`。

**示例：**
```sql
EXPLAIN SELECT film_id FROM (SELECT film_id FROM film) AS dr;
```

| id   | select_type | table | partitions | type  | possible_keys | key                | key_len | ref  | rows | filtered | Extra       |
| ---- | ----------- | ----- | ---------- | ----- | ------------- | ------------------ | ------- | ---- | ---- | -------- | ----------- |
| 1    | SIMPLE      | film  | NULL       | index | NULL          | idx_fk_language_id | 1       | NULL | 1000 | 100.00   | Using index |

**示例：**
```sql
EXPLAIN SELECT film_id FROM (SELECT film_id FROM film) AS dr;
```

| id   | select_type | table | partitions | type  | possible_keys | key                | key_len | ref  | rows | filtered | Extra       |
| ---- | ----------- | ----- | ---------- | ----- | ------------- | ------------------ | ------- | ---- | ---- | -------- | ----------- |
| 1    | SIMPLE      | film  | NULL       | index | NULL          | idx_fk_language_id | 1       | NULL | 1000 | 100.00   | Using index |

**示例：**
```sql
EXPLAIN SELECT film_id FROM film WHERE film_id = 2;
```

| id   | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
| ---- | ----------- | ----- | ---------- | ----- | ------------- | ------- | ------- | ----- | ---- | -------- | ----- |
| 1    | SIMPLE      | film  | NULL       | const | PRIMARY       | PRIMARY | 2       | const | 1    | 100.00   | NULL  |

以上是针对您的笔记进行的整理和修正，希望对您有所帮助！如果您有任何疑问或需要进一步的解释，请随时告诉我。