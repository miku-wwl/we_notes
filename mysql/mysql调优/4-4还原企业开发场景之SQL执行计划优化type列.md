### 4-4 还原企业开发场景之 SQL 执行计划优化

#### 2024年8月15日
#### 16:18

### 执行计划的字段解释与举例
#### type 列

**fulltext**: 全文索引检索。当全文索引和普通索引同时存在时，MySQL 会优先选择使用全文索引，无论其代价如何。这是因为全文索引特别适合处理文本搜索任务。

**ref_or_null**: 类似于 `ref` 方法，但支持对 `NULL` 值的比较。这种类型的实际应用并不常见。

**unique_subquery**: 用于 WHERE 子句中的 IN 形式子查询，其中子查询返回的是唯一的值。MySQL 使用一个临时表存储子查询的结果，并确保结果是唯一的。

**index_subquery**: 当 IN 形式的子查询利用了辅助索引或 IN 常量列表时使用。子查询可能会返回重复值，但通过索引进行去重。

**range**: 表示使用索引进行范围扫描，常见于使用 `>`, `<`, `IS NULL`, `BETWEEN`, `IN`, `LIKE` 等运算符的查询中。

**index_merge**: 表示查询使用了两个或多个索引，并最终合并这些索引的结果（例如，取交集或并集）。这通常发生在 AND 或 OR 条件中使用了不同的索引。

**index**: 索引全表扫描，意味着 MySQL 需要遍历整个索引来获取结果。这通常用于那些只需要访问索引信息而不必访问实际数据行的情况（例如，仅需使用索引列即可完成查询）。

---

### 示例

假设我们有一个 `sakila.actor` 表，该表包含演员的信息，我们可以基于此表来展示一些 SQL 执行计划的例子。

**创建表结构**:
```sql
CREATE TABLE `actor` (
  `actor_id` SMALLINT UNSIGNED NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`actor_id`)
);
```

**全文索引示例**:
```sql
ALTER TABLE actor ADD FULLTEXT (first_name, last_name);

EXPLAIN SELECT * FROM actor WHERE MATCH(first_name, last_name) AGAINST('PENELOPE');
```
在这个例子中，如果 `actor` 表上有全文索引，MySQL 将使用 `fulltext` 类型来执行查询。

**unique_subquery 示例**:
```sql
EXPLAIN SELECT * FROM actor WHERE actor_id IN (SELECT DISTINCT actor_id FROM film_actor WHERE film_id = 1);
```
这里，`film_actor` 表与 `actor` 表关联，`actor_id` 是唯一的，所以使用 `unique_subquery`。

**index_subquery 示例**:
```sql
EXPLAIN SELECT * FROM actor WHERE actor_id IN (SELECT actor_id FROM film_actor WHERE film_id IN (1, 2, 3));
```
在这个例子中，`film_actor` 表上可能存在一个辅助索引，可以用来减少返回的重复 `actor_id`。

**range 示例**:
```sql
EXPLAIN SELECT * FROM actor WHERE first_name LIKE 'P%';
```
使用 `LIKE` 操作符时，如果模式以通配符开始，通常会导致全表扫描；但如果以固定字符开头，则可以使用索引进行范围扫描。

**index_merge 示例**:
```sql
EXPLAIN SELECT * FROM actor WHERE first_name = 'PENELOPE' OR actor_id < 50;
```
这里，`first_name` 和 `actor_id` 可能使用了不同的索引，MySQL 会使用 `index_merge` 来合并这两个索引的结果。

**index 示例**:
```sql
EXPLAIN SELECT actor_id, first_name FROM actor ORDER BY first_name;
```
在这个例子中，我们只关心 `actor_id` 和 `first_name` 字段，因此 MySQL 可以使用 `first_name` 的索引来完成排序操作，而无需访问数据行本身。

以上就是关于执行计划中 `type` 列的解释和一些具体的例子。希望这些信息对您的学习有所帮助！