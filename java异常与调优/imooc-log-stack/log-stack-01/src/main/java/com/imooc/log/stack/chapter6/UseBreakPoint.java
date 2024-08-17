package com.imooc.log.stack.chapter6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>使用断点追踪代码执行过程</h1>
 * */
@SuppressWarnings("all")
public class UseBreakPoint {

    /**
     * <h2>第一种断点: 行断点</h2>
     * */
    private static void lineBreakPoint(String name) {
        // 行断点
        System.out.println(name);
    }

    // 第二种是临时断点, 与行断点几乎是一样的, 只需要勾选 Remove once hit 即可

    /**
     * <h2>第三种断点: 属性断点</h2>
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class FieldWatchPoint {

        // 属性断点
        private String name;

        /**
         * <h2>第四种断点: 方法断点</h2>
         * */
        public void printImoocPrefix() {
            System.out.println("imooc-" + this.name);
        }
    }

    private static class CustomException extends RuntimeException {}

    /**
     * <h2>第五种断点: 异常断点, 只能手动配置</h2>
     * */
    private static void exceptionBreakPoint() {

        try {
            throw new CustomException();
        } catch (CustomException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // 行断点
        lineBreakPoint("qinyi");

        // 属性断点
//        FieldWatchPoint watchPoint = new FieldWatchPoint();
//        watchPoint.setName("qinyi");
//        System.out.println(watchPoint);

        // 方法断点
//        FieldWatchPoint watchPoint = new FieldWatchPoint("qinyi");
//        watchPoint.printImoocPrefix();

        // 异常断点
//        exceptionBreakPoint();
    }
}
