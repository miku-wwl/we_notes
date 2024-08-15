### 执行计划字段解释与举例

#### `table` 列
- **定义**：显示查询所涉及的表名。如果查询使用了别名，则显示别名；如果查询不直接涉及数据表操作，则显示为 `NULL`；如果显示为 `<derivedN>`，则表示这是一个由子查询产生的临时表，其中 `N` 是执行计划中的 ID；如果显示为 `<unionM,N>`，则表示这是由 `UNION` 操作产生的临时表，其中 `M` 和 `N` 是执行计划中涉及的查询 ID。

#### `type` 列
- **定义**：表示MySQL访问表的方式。
  - **system**：当表中只有一行数据或为空时使用，仅适用于 MyISAM 和 MEMORY 引擎。对于 InnoDB 表，这种情况下通常显示为 `ALL` 或 `INDEX`。
  - **const**：使用唯一索引或主键进行等值匹配时，返回的记录必定是一行。在其他数据库系统中，这被称为“唯一索引扫描”。

#### MySQL 示例

```sql
-- 创建表 t4
CREATE TABLE t4 (
    id INT DEFAULT NULL,
    name VARCHAR(25) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 将引擎更改为 MyISAM
ALTER TABLE t4 ENGINE = MyISAM;

-- 查看执行计划
EXPLAIN SELECT * FROM t4 WHERE id = 1;
```

**输出结果**

| id | select_type | table | partitions | type             | possible_keys | key     | key_len | ref   | rows | filtered    | Extra          |
|----|-------------|-------|------------|------------------|---------------|---------|---------|-------|------|-------------|----------------|
|  1 | SIMPLE      | t4    | NULL      | system           | NULL          | NULL    | NULL    | NULL  |     1 |    100.00   | NULL           |


```plaintext
1 row in set (0.00 sec)
```

#### `type` 列（续）

- **eq_ref**：出现在多表连接查询中，驱动表循环获取数据。这一行数据是第二个表的主键或唯一索引，作为条件查询只返回一条数据，并且该索引列不允许为 `NULL`。当使用多列的唯一索引或主键时，所有列都必须参与比较才会出现 `eq_ref`。
- **ref**：与 `eq_ref` 不同，它不要求连接顺序，也不需要是主键或唯一索引。只要使用等值条件检索就会出现，常见于辅助索引的等值查找或在多列主键、唯一索引中使用第一个列以外的列作为等值查找。

---

### sakila 数据库案例

假设我们想要优化一个涉及 `actor` 和 `film_actor` 表的查询。这两个表之间的关系是通过 `actor_id` 建立的。

```sql
-- 查询演员信息以及他们出演的电影
SELECT a.actor_id, a.first_name, a.last_name, f.title
FROM actor a
JOIN film_actor fa ON a.actor_id = fa.actor_id
JOIN film f ON fa.film_id = f.film_id
WHERE a.actor_id = 1;
```

**执行计划示例**

| id | select_type | table | partitions | type          | possible_keys   | key             | key_len | ref         | rows | filtered | Extra       |
|----|-------------|-------|------------|---------------|-----------------|-----------------|---------|-------------|------|----------|-------------|
|  1 | SIMPLE      | a     | NULL       | const         | PRIMARY         | PRIMARY         | 4       | const       |    1 |   100.00 | Using index |
|  1 | SIMPLE      | fa    | NULL       | eq_ref        | idx_actor_id    | idx_actor_id    | 5       | const,const |    1 |   100.00 | Using index |
|  1 | SIMPLE      | f     | NULL       | ref           | PRIMARY,idx_title | PRIMARY,idx_title | 5       | sakila.fa.film_id |  1 |   100.00 | Using index |


在这个例子中：
- `a` 表使用 `const` 类型，因为通过主键 `actor_id` 进行了等值匹配，只返回一行记录。
- `fa` 表使用 `eq_ref` 类型，因为它基于 `a` 表的主键进行等值匹配，并且只返回一行记录。
- `f` 表使用 `ref` 类型，因为它基于 `fa` 表的 `film_id` 进行等值匹配，并且可能返回多行记录。

这样我们就完成了对执行计划的理解和优化过程。希望这些信息对您的学习有所帮助！