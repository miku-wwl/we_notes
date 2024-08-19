package com.imooc.log.stack.chapter3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * <h1>异常发生时不要影响系统的状态</h1>
 * */
@SuppressWarnings("all")
public class DoNotAffectTheState {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Imoocer {
        private String name;
        private String birthday;  // 接受的是 yyyy-MM-dd 结构
        private int age;

        public Imoocer(String name) {
            this.name = name;
        }
    }

    // 参数是不可变的, 状态就不会变
    public void printImoocer(final Imoocer imoocer) {
        System.out.println(imoocer.getName());
        // 由于参数是 final 的, 所以, 不可改变
//        imoocer = new Imoocer("qinyi");
        imoocer.setAge(19);
    }

    // 如果对象可变, 保持好状态

    // 第一种, 在执行操作之前检查参数的有效性
    public static void append(List<Integer> source, List<Integer> target) {

        assert null != source && null != target;
        source.forEach(s -> {
            if (null != s) {
                target.add(s);      // 参数有效性校验
            }
        });

        // do something
    }

    // 第二种, 调整计算处理过程中的顺序
    public static void computeAge(Imoocer imoocer, String birthday) {

        assert null != imoocer;

        LocalDate today = LocalDate.now();
        LocalDate playerDate =
                LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(birthday));
        long years = ChronoUnit.YEARS.between(playerDate, today);

        imoocer.setBirthday(birthday);
        imoocer.setAge((int) years);
    }

    // 第三种, 编写恢复代码, 回滚到之前的状态
    public static void transaction() {

        Connection conn = null;

        try {
            conn.setAutoCommit(false);
            // 执行很多 SQL 语句
            conn.commit();
        } catch (SQLException ex) {
            // 回滚事物
//            conn.rollback();
        } finally {
//            conn.setAutoCommit(true);
//            conn.close();
        }
    }
}


这段代码展示了在处理异常时如何确保系统的状态不受影响。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **不可变参数**:
   - `printImoocer` 方法接受一个 `final` 类型的 `Imoocer` 对象作为参数。
   - 通过将参数声明为 `final`，确保了方法内部不能改变该对象的引用，但仍然可以改变其内部状态（如年龄）。

2. **参数有效性校验**:
   - `append` 方法在执行操作前对输入参数进行校验，确保它们不是 `null`。
   - 使用断言 `assert` 来检查参数是否为 `null`，并在循环中进一步检查每个元素的有效性。

3. **计算处理过程中的顺序调整**:
   - `computeAge` 方法计算 Imoocer 的年龄，并更新对象的状态。
   - 先计算年龄，再更新对象的 `birthday` 和 `age` 属性，确保即使在计算过程中发生异常，也不会留下不一致的状态。

4. **编写恢复代码**:
   - `transaction` 方法展示了如何在数据库事务中使用回滚来恢复到之前的状态。
   - 使用 `try-catch-finally` 结构来确保无论是否发生异常，事务都能够被适当地提交或回滚。

### 调优思想

1. **不可变性**:
   - 使用 `final` 参数可以确保方法内部不会改变对象的引用，有助于保持状态的一致性。
   - 如果对象的内部状态是可以改变的，那么应该谨慎考虑是否允许方法内部改变这些状态。

2. **参数有效性校验**:
   - 在方法开始时检查参数的有效性，可以预防因无效参数而导致的异常。
   - 使用断言来检查参数，确保在生产环境中禁用断言时仍能正常运行。

3. **处理顺序调整**:
   - 调整方法内部的处理顺序，确保即使发生异常，也不会留下不一致的状态。
   - 先进行计算或数据处理，然后再更新状态，可以减少异常导致的数据不一致性。

4. **编写恢复代码**:
   - 在事务处理中使用回滚来恢复到之前的状态，可以确保即使发生异常也能保证数据的一致性。
   - 使用 `try-catch-finally` 结构确保在发生异常时能够执行必要的清理工作。

5. **异常处理的一致性**:
   - 通过确保在异常发生时系统的状态不受影响，可以提高系统的稳定性和可靠性。

### 实际应用场景

在实际应用中，这种确保系统状态不受异常影响的做法适用于以下场景：
- 当你需要确保即使发生异常，系统也能保持一致的状态时。
- 当你需要在事务处理中确保数据的一致性时。
- 当你需要确保方法的参数有效以避免异常时。

总结来说，这段代码示例通过展示如何确保系统状态不受异常影响，体现了不可变性、参数有效性校验、处理顺序调整以及编写恢复代码等调优思想。这对于提高代码的质量和系统的稳定性非常重要。