### MySQL 5.6 Order By 排序优化 & 索引生效案例

#### 2024年8月16日
#### 11:36

**Order By 排序不一致问题**

在 MySQL 5.6 及更高版本中，对于带有 `LIMIT M, N` 的 `ORDER BY` 语句，在内存层面做了一个优化，引入了优先队列排序算法。这种算法基于堆排序，能够有效减少排序所需的内存空间。

**堆排序算法特点**
- 在 `LIMIT M, N` 类型的查询中，只需要 `M + N` 个元素的排序缓冲区（sort buffer）。
- 对于升序排序，使用大顶堆；对于降序排序，使用小顶堆。
- 最终堆中的元素组成了排序结果的前 `N` 个元素。

**慢查询优化思路及案例**

**Order By 排序不一致问题**
- **原因分析**：当使用 `LIMIT M, N` 并且存在多个相同的排序键值时，堆排序是非稳定的，可能导致分页重复。
- **解决方案**：在排序字段中加入一个唯一的字段，如主键 `id`，以确保排序键值的唯一性。

**案例演示**

假设我们有一个表 `t3`，包含以下字段：`id`, `idc`, 和 `name`。我们将使用 sakila 数据库来模拟这个表的数据结构，并创建一个类似的表来进行测试。

1. **创建示例表 `t3`**
   ```sql
   CREATE TABLE t3 (
       id INT AUTO_INCREMENT PRIMARY KEY,
       idc INT,
       name VARCHAR(50)
   );
   ```

2. **插入数据**
   ```sql
   INSERT INTO t3 (idc, name) VALUES (3, 'Alice'), (3, 'Bob'), (3, 'Charlie');
   INSERT INTO t3 (idc, name) VALUES (4, 'David'), (4, 'Eve');
   ```

3. **展示表结构和数据**
   ```sql
   DESC t3;
   SELECT * FROM t3;
   ```

4. **演示查询**
   ```sql
   EXPLAIN SELECT idc, name FROM t3 WHERE id > 2 AND id < 10 ORDER BY idc, name, id \G
   ```

在这个查询中，`idc` 和 `name` 是排序字段，`id` 作为唯一的附加字段来确保排序的一致性。`WHERE` 子句限制了返回的行数。我们使用 `EXPLAIN` 关键字来查看执行计划。

**索引生效情况分析**
- 如果对 `idc` 和 `name` 创建了索引，那么 `ORDER BY` 将能直接利用这些索引进行排序。
- 如果只对 `idc` 创建了索引，则 MySQL 可能需要额外的排序步骤。
- 如果没有索引，则需要全表扫描并进行排序。

接下来，我们可以根据上述步骤在 sakila 数据库中找到一个类似的数据表来进行实际操作。例如，我们可以使用 `film` 表来模拟 `t3` 表，并尝试执行类似的查询。

如果你想要具体的 sakila 数据库中的例子，请告诉我，我可以为你提供更详细的示例。