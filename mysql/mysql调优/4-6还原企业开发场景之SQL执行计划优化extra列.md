### 执行计划的字段解释与举例 - Extra 列

#### 2024年8月15日 17:09

**Extra 列解释**

- **Using where**: 表示查询的列被索引覆盖，但 WHERE 筛选条件是索引列之一但不是索引的前导列，意味着无法直接通过索引查找来查询到符合条件的数据。
  
  **例**:
  ```sql
  SELECT * FROM sakila.film
  WHERE film_id IN (SELECT film_id FROM sakila.inventory WHERE store_id = 2)
  AND rating = 'PG';
  ```
  这个查询可能会显示 `Using where`，因为虽然 `rating` 是 `film` 表的一个索引列，但它不是在 `inventory` 表中使用的索引的前导列。

- **Using index condition**: 类似于 `Using where`，但查询的列不完全被索引覆盖，WHERE 条件中是一个前导列的范围。
  
  **例**:
  ```sql
  EXPLAIN SELECT title, description FROM sakila.film
  WHERE film_id BETWEEN 100 AND 200;
  ```
  如果 `film_id` 是索引的前导列，那么这个查询可能会使用 `Using index condition`，这样就可以避免全表扫描。

**Extra 列解释**

- **Using temporary**: 表示使用了临时表存储中间结果。临时表可以是内存临时表或磁盘临时表，执行计划中看不出来，需要查看状态变量 `Used_tmp_table` 或 `Used_tmp_disk_table` 才能看出来。
  
  **例**:
  ```sql
  SELECT COUNT(*) FROM sakila.customer c
  JOIN sakila.rental r ON c.customer_id = r.customer_id
  GROUP BY c.store_id;
  ```
  如果在没有适当索引的情况下进行分组操作，可能会使用临时表。

- **Using filesort**: MySQL 对结果使用一个外部排序算法，而不是按索引顺序从表中读取行。这意味着 MySQL 需要根据连接类型浏览所有符合条件的记录，并保存排序关键字和行指针，然后对关键字进行排序并按顺序检索行信息。
  
  **例**:
  ```sql
  SELECT * FROM sakila.actor a
  JOIN sakila.film_actor fa ON a.actor_id = fa.actor_id
  ORDER BY a.first_name;
  ```
  如果没有适当的索引来支持排序操作，MySQL 可能会使用文件排序。

**Extra 列解释**

- **Using intersect/Using union/Using sort_union/Using sort_intersection**: 这些信息分别表示当使用 `AND` 和 `OR` 连接多个索引条件时，MySQL 如何处理这些条件以获取交集、并集或排序合并后的结果集。
  
  **例**:
  ```sql
  SELECT * FROM sakila.actor
  WHERE first_name LIKE 'A%' OR last_name LIKE 'A%';
  ```
  如果 `first_name` 和 `last_name` 分别有独立的索引，那么这个查询可能会使用 `Using union`。

**Extra 列解释**

- **FirstMatch (table_name)**: 自 MySQL 5.6 开始引入的优化子查询的新特性之一，常见于 WHERE 子句含有 `IN` 类型的子查询。如果内表的数据量比较大，就可能出现这个提示。
  
  **例**:
  ```sql
  SELECT * FROM sakila.actor a
  WHERE a.actor_id IN (SELECT actor_id FROM sakila.film_actor WHERE film_id = 1);
  ```
  如果 `film_actor` 表数据量较大，这个查询可能会使用 `FirstMatch`。

- **LooseScan (m..n)**: MySQL 5.6 之后引入的优化子查询的新特性之一，在 `IN` 类型的子查询中，子查询返回的可能有重复记录时，就可能出现这个提示。
  
  **例**:
  ```sql
  SELECT * FROM sakila.actor a
  WHERE a.actor_id IN (SELECT actor_id FROM sakila.film_actor WHERE film_id IN (1, 2));
  ```
  如果 `film_actor` 表中有重复的 `actor_id`，则此查询可能会使用 `LooseScan`。

### Filtered 列

- **Filtered 列**: 使用 `EXPLAIN EXTENDED` 时会出现这个列，5.7 之后的版本默认就有这个字段，不需要使用 `EXPLAIN EXTENDED` 了。这个字段表示存储引擎返回的数据在 server 层过滤后，剩下多少满足查询的记录数量的比例，注意是百分比，不是具体记录数。
  
  **例**:
  ```sql
  EXPLAIN SELECT * FROM sakila.film f
  WHERE f.rating = 'PG' AND f.length > 100;
  ```
  在执行计划中，`Filtered` 列将显示存储引擎返回的记录中，符合 `length > 100` 这个条件的比例。

这些例子可以帮助理解 MySQL 中不同执行计划的含义以及如何优化查询。希望这有助于您的学习！