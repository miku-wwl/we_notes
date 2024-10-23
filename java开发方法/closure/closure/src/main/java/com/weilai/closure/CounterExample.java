package com.weilai.closure;

import java.util.function.Consumer;

public class CounterExample {

    public static void main(String[] args) {
        // 创建一个 Consumer<Runnable> 的 Lambda 表达式
        Consumer<Runnable> executeMultipleTimes = runnable -> {
            for (int i = 0; i < 5; i++) {
                runnable.run();
            }
        };

        // 创建一个 Runnable 的 Lambda 表达式，在内部定义变量
        Runnable incrementAndPrint = () -> {
            int count = 0; // 在 Lambda 内部定义的变量

            // 模拟每次调用时变量的递增
            System.out.println("Lambda 被调用，当前 count 值：" + count++);
        };


        // 执行多次 Lambda
        executeMultipleTimes.accept(incrementAndPrint);
    }
}