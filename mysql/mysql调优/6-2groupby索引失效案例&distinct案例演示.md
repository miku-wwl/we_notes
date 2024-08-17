### MySQL 调优笔记 - Group By 和 Distinct 示例

**日期:** 2024年8月16日 14:54

#### 慢查询优化思路及案例

**Group By 分组案例演示**

- **演示 SQL 及思路:**
  ```sql
  EXPLAIN SELECT idc, MAX(name) FROM t3 WHERE id > 2 AND id < 10 GROUP BY idc, name, id\G
  ```

  - **分析:**
    - 这个查询尝试在 `t3` 表中选择 `idc`、`MAX(name)` 并按照 `idc`、`name` 和 `id` 分组。
    - 如果表上有索引 `(idc, name, id)` 或者一个可以覆盖所有查询字段的多列索引，则查询将能够有效地利用索引来完成分组操作。
    - 如果没有合适的索引，MySQL 将需要读取所有数据行并进行排序，这会导致性能下降。

#### Distinct 的实现及优化思路

- **Distinct 的原理:**
  - `DISTINCT` 实际上与 `GROUP BY` 非常相似。它用于从结果集中去除重复的行。
  - `DISTINCT` 的实现可以通过松散索引扫描或紧凑索引扫描来完成，如果不能仅通过索引完成，则 MySQL 可能会使用临时表。
  - 与 `GROUP BY` 不同的是，`DISTINCT` 不需要对结果集进行排序。

- **Distinct 案例演示:**

  1. **索引中完成 (无排序问题):**
     ```sql
     EXPLAIN SELECT DISTINCT name FROM t3 WHERE idc = 3\G
     ```
     - **分析:**
       - 如果有一个索引包含 `idc` 和 `name`，并且 `idc` 是索引的第一列，那么这个查询可以直接通过索引完成。
       - 因为 `idc` 是一个确定值 (3)，查询可以快速定位到相应的索引条目，然后从中挑选出唯一的 `name` 值。

  2. **非索引中完成 (暗藏排序问题):**
     ```sql
     EXPLAIN SELECT DISTINCT name FROM t3 WHERE idc > 1\G
     ```
     - **分析:**
       - 当 `idc > 1` 时，如果没有合适的索引，MySQL 将需要读取多个数据行，并可能需要创建临时表来存储结果集中的唯一 `name` 值。
       - 在这种情况下，如果索引不完全覆盖查询字段或者不按查询顺序排列，可能会出现额外的排序操作。

---

接下来，让我们使用 sakila 数据库中的具体表来模拟这些查询。假设我们使用 `actor` 表来代替 `t3` 表。

**Group By 示例:**
```sql
EXPLAIN SELECT actor_id, MAX(first_name) FROM sakila.actor WHERE actor_id BETWEEN 2 AND 10 GROUP BY actor_id, first_name, actor_id;
```

**Distinct 示例:**

1. **索引中完成 (无排序问题):**
   ```sql
   EXPLAIN SELECT DISTINCT first_name FROM sakila.actor WHERE actor_id = 3\G
   ```
   - **注释:**
     - 如果有一个索引 `(actor_id, first_name)`，则这个查询可以仅通过索引完成。

2. **非索引中完成 (暗藏排序问题):**
   ```sql
   EXPLAIN SELECT DISTINCT first_name FROM sakila.actor WHERE actor_id > 1\G
   ```
   - **注释:**
     - 如果没有索引或者索引不足以支持查询，MySQL 可能需要执行全表扫描，并可能需要额外的排序步骤。

请根据您的 MySQL 版本和 sakila 数据库的具体情况进行测试，以获得更准确的结果。