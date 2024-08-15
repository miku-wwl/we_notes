### 3-6 利用 pt-query-digest 查找三大类有问题的 SQL

**日期:** 2024年8月15日  
**时间:** 14:38

1. **查询次数多且每次查询占用时间长的 SQL**

   - 通常这类 SQL 会出现在 `pt-query-digest` 分析结果的前列。此工具可以清晰地展示每个 SQL 的执行次数及其所占比例等信息。执行次数多、占比大的 SQL 需要重点关注。

2. **I/O 大的 SQL**

   - 注意 `pt-query-digest` 分析中的 `Rows_examined` 项，该值表示为了执行查询而需要扫描的行数。扫描的行数越多，意味着 I/O 操作量越大。

3. **未命中的索引的 SQL**

   - 通过比较 `pt-query-digest` 中的 `Rows_examined` 和 `Rows_sent` 可以判断 SQL 的索引命中率。如果两者相差较大，则表明索引使用效率不高，这样的 SQL 应当重点分析。

---

### 使用 MySQL sakila 数据库的具体案例

#### 1. 查询次数多且每次查询占用时间长的 SQL

假设我们使用 `pt-query-digest` 工具对 `sakila` 数据库进行了分析，发现以下 SQL 查询频繁且耗时较长：

```sql
SELECT * FROM film WHERE title LIKE 'A%';
```

此查询可能因为全表扫描而导致较高的执行频率和执行时间。优化建议包括：
- 建立针对 `title` 字段的索引。
- 修改查询语句只选择必要的列而不是使用 `*`。

#### 2. I/O 大的 SQL

一个示例 SQL 如下：

```sql
SELECT * FROM actor JOIN film_actor USING (actor_id) WHERE actor.first_name = 'PENELOPE';
```

该查询可能导致大量的 I/O 操作，因为它需要扫描整个 `film_actor` 表来找到与 `actor_id` 匹配的所有记录。优化建议包括：
- 建立针对 `actor.first_name` 的索引。
- 如果可能，考虑使用覆盖索引（covering index）减少 I/O。

#### 3. 未命中的索引的 SQL

一个示例 SQL 如下：

```sql
SELECT * FROM film WHERE description LIKE '%action%';
```

这个查询可能不会使用任何现有的索引，因为 `LIKE` 子句以通配符开头。优化建议包括：
- 创建一个全文索引在 `description` 字段上。
- 或者改变查询逻辑，避免以通配符作为 `LIKE` 子句的开头。

以上案例展示了如何利用 `pt-query-digest` 来识别并优化 MySQL `sakila` 数据库中的问题 SQL。