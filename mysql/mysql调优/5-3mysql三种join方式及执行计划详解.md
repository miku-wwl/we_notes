### 慢查询优化思路及案例

#### 优化思路
- **永远用小结果集驱动大的结果集**（JOIN操作表小于百万级别）
- **驱动表的定义**
  - 当进行多表连接查询时，驱动表的定义为：
    1. 指定了联接条件时，满足查询条件的记录行数较少的表为**驱动表**。
    2. 未指定联接条件时，行数较少的表为**驱动表**。

#### 案例
假设我们有以下两个表：
- `film` (较大的表，例如包含所有电影信息)
- `actor` (较小的表，包含演员信息)

如果我们想找出某位演员出演的所有电影，我们可以考虑将`actor`作为驱动表，因为它可能比`film`表小很多。

```sql
SELECT f.title AS film_title, a.first_name, a.last_name
FROM actor a
JOIN film_actor fa ON a.actor_id = fa.actor_id
JOIN film f ON fa.film_id = f.film_id
WHERE a.first_name = 'PENELOPE' AND a.last_name = 'GUINESS';
```

### JOIN 类型的区别

#### LEFT JOIN
- 返回左表的所有记录以及右表中匹配的记录。如果右表中没有匹配，则返回 NULL 值。

```sql
SELECT t2.*, t3.*
FROM sakila.actor t2
LEFT JOIN sakila.film_actor t3 ON t2.actor_id = t3.actor_id
AND t3.actor_id IN (1, 2, 3);
```

#### RIGHT JOIN
- 返回右表的所有记录以及左表中匹配的记录。如果左表中没有匹配，则返回 NULL 值。

```sql
SELECT t2.*, t3.*
FROM sakila.actor t2
RIGHT JOIN sakila.film_actor t3 ON t2.actor_id = t3.actor_id
AND t3.actor_id IN (1, 2, 3);
```

#### INNER JOIN
- 只返回两个表中匹配的记录。

```sql
SELECT t2.*, t3.*
FROM sakila.actor t2
INNER JOIN sakila.film_actor t3 ON t2.actor_id = t3.actor_id
AND t3.actor_id IN (1, 2, 3);
```

### 执行计划详解
为了理解上述查询的执行效率，可以查看执行计划。这可以通过添加 `EXPLAIN` 关键字到查询前实现：

```sql
EXPLAIN SELECT t2.*, t3.*
FROM sakila.actor t2
INNER JOIN sakila.film_actor t3 ON t2.actor_id = t3.actor_id
AND t3.actor_id IN (1, 2, 3);
```

通过查看 `EXPLAIN` 输出的结果，我们可以了解查询的执行顺序、使用的索引等信息，从而判断是否需要进一步优化查询。

希望这些示例能帮助您更好地理解您的笔记内容。如果您有其他具体问题或者需要更详细的解释，请随时告诉我！