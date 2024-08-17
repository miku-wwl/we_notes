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
