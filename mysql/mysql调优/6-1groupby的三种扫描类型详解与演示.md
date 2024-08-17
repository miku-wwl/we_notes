### 慢查询优化思路及案例 - Group By 分组优化

#### 日期
2024年8月16日 14:32

#### Group By 分组优化思路
Group By 分组操作在 MySQL 中可以被优化以提高查询性能。优化的关键在于如何减少数据处理量以及尽可能利用索引来避免排序和临时表的使用。

#### Group By 的类型
MySQL 在执行 Group By 时有三种不同的实现方式：
1. **Loose Index Scan (松散的索引扫描)**
2. **Tight Index Scan (紧凑的索引扫描)**
3. **Using Temporary (使用临时表)**

#### Loose Index Scan (松散的索引扫描)
- **扫描过程**:
  - 根据 `GROUP BY` 后面的字段进行分组，分组时不需要读取所有索引中的 key。
  - 例如，有一个索引 `(key1, key2, key3)`，如果 `GROUP BY key1, key2`，则只需要读取索引中的 `key1` 和 `key2`。
  - 然后根据 `WHERE` 条件进行筛选。
  

**示例**:
假设我们想要统计每个城市的顾客数量。
```sql
SELECT city, COUNT(*) AS num_customers
FROM sakila.address a
JOIN sakila.customer c ON a.address_id = c.address_id
GROUP BY city;
```

#### Tight Index Scan (紧凑的索引扫描)
- **扫描过程**:
  - 紧凑索引扫描需要在扫描索引时读取所有满足条件的索引键。
  - 然后根据读取的数据完成 `GROUP BY` 操作得到结果。
  - 紧凑索引扫描是先执行 `WHERE` 操作，再进行分组；而松散索引扫描则是先分组再筛选。
  

**示例**:
如果我们想统计活动状态为 'active' 的顾客在各个城市的数量。
```sql
SELECT city, COUNT(*) AS num_active_customers
FROM sakila.address a
JOIN sakila.customer c ON a.address_id = c.address_id
WHERE c.active = 1
GROUP BY city;
```

#### Using Temporary (使用临时表)
- **扫描过程**:
  - 当 MySQL Query Optimizer 无法找到合适的索引可以利用时，它会先读取需要的数据，然后通过临时表来完成 `GROUP BY` 操作。
  

**示例**:
如果我们没有合适的索引，并且需要统计每个城市中有多少个不同状态的顾客。
```sql
SELECT city, active, COUNT(*) AS num_customers
FROM sakila.address a
JOIN sakila.customer c ON a.address_id = c.address_id
GROUP BY city, active;
```

### 总结
- **Loose Index Scan** 是先分组后筛选，适用于简单分组场景。
- **Tight Index Scan** 是先筛选后分组，适用于更复杂的查询场景。
- **Using Temporary** 使用临时表，通常发生在没有合适索引的情况下，性能较差。

希望这些例子能帮助您更好地理解 MySQL 中 `GROUP BY` 的不同实现方式及其优化策略。如果您需要更多关于 MySQL 调优的信息，请随时告诉我。