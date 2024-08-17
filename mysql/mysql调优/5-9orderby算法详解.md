### Order By 排序原理及优化思路

**Order By 排序可利用索引进行优化：**
- 如果 `ORDER BY` 子句中的字段是索引的前导列，那么可以直接在索引中进行排序，而不需要额外的内存或文件排序。

**不能利用索引避免额外排序的情况：**
- 如果排序字段中包含了多个索引，但排序顺序与索引键的顺序不一致（即非前导列）。

### 慢查询优化思路及案例

**Order By 排序算法**

MySQL 对于无法直接通过索引排序的 SQL 查询，会自行实现排序功能，这时执行计划中会出现 "Using filesort"。注意 "filesort" 并不一定意味着文件排序，也可能是在内存中排序，这取决于 `sort_buffer_size` 参数以及结果集的大小。MySQL 实现排序的方式主要包括常规排序、优化排序和优先队列排序，涉及的排序算法有快速排序、归并排序和堆排序。

#### Order By 常规排序算法

**步骤：**
1. 从表 `t1` 中获取满足 `WHERE` 条件的记录。
2. 对于每条记录，将记录的主键 + 排序键 (`id`, `col2`) 取出放入 `sort buffer`。
3. 如果 `sort buffer` 可以存放所有满足条件的 `(id, col2)` 对，则进行排序；否则当 `sort buffer` 满后，进行排序并固化到临时文件中。排序算法采用的是快速排序算法。
4. 若排序过程中产生了临时文件，则需要利用归并排序算法确保临时文件中的记录是有序的。
5. 循环执行上述过程，直到所有满足条件的记录都参与了排序。
6. 扫描排好序的 `(id, col2)` 对，并利用 `id` 去获取 `SELECT` 需要返回的列 (`col1`, `col2`, `col3`)。
7. 将获取的结果集返回给用户。

**MySQL 配置示例：**
```sql
mysql> SHOW VARIABLES LIKE '%sort_buffer_size%';
+----------------------+----------+
| Variable_name        | Value    |
+----------------------+----------+
| innodb_sort_buffer_size | 1048576 |
| myisam_sort_buffer_size | 8388608 |
| sort_buffer_size     | 262144   |
+----------------------+----------+
3 rows in set (0.00 sec)

mysql> SHOW VARIABLES LIKE '%read_rnd_buffer_size%';
+-----------------------+----------+
| Variable_name         | Value    |
+-----------------------+----------+
| read_rnd_buffer_size  | 262144   |
+-----------------------+----------+
1 row in set (0.02 sec)
```

#### Order By 优化排序算法

**常规排序方式** 除了排序本身外，还需要额外两次 I/O。**优化的排序方式** 相较于常规排序，减少了第二次 I/O。主要区别在于放入 `sort buffer` 的不是 `(id, col2)` 而是 `(col1, col2, col3)`。由于 `sort buffer` 中包含了查询需要的所有字段，因此排序完成后可以直接返回结果，无需再次检索数据。这种方式的代价在于，相同大小的 `sort buffer` 下，能存放的 `(col1, col2, col3)` 数目要少于 `(id, col2)`，如果 `sort buffer` 不够大，可能会导致需要写入临时文件，造成额外的 I/O。

**MySQL 配置示例：**
```sql
mysql> SHOW VARIABLES LIKE '%max_length_for_sort_data%';
+-----------------------------+--------+
| Variable_name               | Value  |
+-----------------------------+--------+
| max_length_for_sort_data    | 4096   |
+-----------------------------+--------+
1 row in set (0.02 sec)
```

#### Order By 优先队列排序算法

从 MySQL 5.6 及以后的版本开始，针对 `ORDER BY LIMIT M, N` 语句，在空间层面做了优化，引入了一种新的排序方式——优先队列，这种方式采用堆排序实现。堆排序算法的特性非常适合解决 `LIMIT M, N` 这类排序问题，虽然仍然需要所有元素参与排序，但是只需要 `M + N` 个元组的 `sort buffer` 空间即可。对于 `M` 和 `N` 较小的场景，基本不会因为 `sort buffer` 不足而导致需要临时文件进行归并排序的问题。对于升序排序，采用大顶堆；对于降序排序，采用小顶堆。

### 示例

假设我们有一个 `rentals` 表，它有以下结构：

```sql
CREATE TABLE rentals (
  rental_id INT PRIMARY KEY,
  customer_id INT,
  rental_date DATE,
  amount DECIMAL(5,2),
  INDEX idx_customer_date (customer_id, rental_date)
);
```

现在我们要找出每个客户的前三次最昂贵的租赁记录：

```sql
SELECT r.rental_id, r.customer_id, r.amount
FROM rentals r
WHERE (r.customer_id, r.amount) IN (
  SELECT customer_id, amount
  FROM rentals
  GROUP BY customer_id
  ORDER BY amount DESC
  LIMIT 3
)
ORDER BY r.customer_id, r.amount DESC;
```

这个查询可以通过优先队列排序算法进行优化，因为它涉及到 `LIMIT` 和 `ORDER BY`。对于每个客户，MySQL 可以创建一个小顶堆来存储前三次最昂贵的租赁记录，并且只需要足够大的 `sort buffer` 来保存每个客户最多三个记录的信息即可完成排序任务。