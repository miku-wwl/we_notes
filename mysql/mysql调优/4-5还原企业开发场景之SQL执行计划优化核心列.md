### SQL执行计划优化核心列

#### 2024年8月15日 16:40

**Type 列**

- **All**: 表示全表扫描，即MySQL需要扫描整张表的所有行。
- **System**: 如果表只有一行，则MySQL优化器将它视为常量表。
- **Const / Eq_ref**: 当连接查询中的一侧表可以通过主键或唯一索引找到唯一匹配的行时使用。
- **Ref**: 使用非唯一索引或覆盖索引（如果索引包含所有需要选择的列）。
- **Fulltext**: 全文索引搜索。
- **Ref_or_null**: 类似于`Ref`，但可以匹配`NULL`值。
- **Unique_subquery**: 子查询返回唯一值。
- **Index_subquery**: 子查询使用索引。
- **Range**: 查询使用索引的一部分，通常是通过范围查询或索引前缀。
- **Index_merge**: MySQL可以合并多个索引来返回结果。
- **Index**: 只使用索引来检索数据。
- **ALL**: 没有使用索引，性能最差。

通常情况下，好的查询应该至少达到`Range`级别，最好是`Ref`级别。

**Possible_keys 列**
- 列出所有可能用来执行查询的索引。

**Key 列**
- 显示MySQL决定实际使用的索引。对于`index_merge`类型的查询，可能会列出多个索引。

**Key_len 列**
- 显示索引中用于查询的列的总字节数。对于多列索引，只计算实际使用的列的长度。

**Ref 列**
- 显示了哪些列或常量被用来查找行。对于等值连接，这里会显示另一个表中的列名。如果使用了函数或表达式，这里会显示`func`。

**Rows 列**
- 显示MySQL预计需要检查的行数。

**Extra 列**
- 提供额外的信息，例如：
  - **Notable_used**: 表明查询没有使用任何索引。
  - **Using where**: 表示MySQL将在存储引擎层应用WHERE子句的限制。
  - **Using index**: 表示MySQL使用索引覆盖查询。
  - **Using index condition**: 类似于`Using where`，但是MySQL在存储引擎层对索引进行了过滤。

### 示例

假设我们使用`sakila`数据库中的`actor`表和`film_actor`表来进行一些查询示例。

**Example 1: Select actors with a specific last name**
```sql
EXPLAIN SELECT * FROM actor WHERE last_name = 'DAVIS';
```

**Output (Simplified)**


| id | select_type | table | type   | possible_keys | key     | key_len | ref   | rows | Extra       |
|----|-------------|-------|--------|---------------|---------|---------|-------|------|-------------|
|  1 | SIMPLE      | actor | ref    | PRIMARY       | PRIMARY | 93      | const |    1 | Using where |

**Explanation**

- `type`: `ref`表示使用了索引。
- `possible_keys`: `PRIMARY`表示可能使用到的索引。
- `key`: `PRIMARY`表示实际上使用的索引。
- `key_len`: `93`表示索引的长度。
- `ref`: `const`表示使用了一个常量值来匹配`last_name`。
- `rows`: `1`表示预计只需要检查一行。

**Example 2: Select films with actors using an index merge**
```sql
EXPLAIN SELECT film_id FROM film_actor fa JOIN actor a ON fa.actor_id = a.actor_id WHERE a.first_name = 'PENELOPE' AND a.last_name = 'GUINESS';
```

**Output (Simplified)**

| id | select_type | table | type       | possible_keys | key              | key_len | ref   | rows | Extra                                        |
|----|-------------|-------|------------|---------------|------------------|---------|-------|------|----------------------------------------------|
|  1 | SIMPLE      | a     | ref        | PRIMARY       | PRIMARY          | 93      | const |    1 | Using where                                  |
|  1 | SIMPLE      | fa    | index_merge| actor_id      | actor_id, idx_fk | 5, 5    | NULL  |    1 | Using index; Using index_merge(idx_fk, actor_id) |

**Explanation**
- `type`: `index_merge`表示MySQL使用了两个不同的索引来找到匹配的行。
- `possible_keys`: 对`fa`表列出了可能的索引。
- `key`: 对`fa`表列出了实际使用的索引。
- `key_len`: 对`fa`表列出了每个索引使用的长度。
- `Extra`: 表明使用了索引合并策略，并且使用了索引覆盖查询。

这些示例可以帮助您更好地理解MySQL的执行计划以及如何根据执行计划优化查询。