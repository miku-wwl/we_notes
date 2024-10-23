package com.weilai.closure;

import java.util.function.Consumer;

public class CounterClosureExample {

    public static void main(String[] args) {
        // 使用闭包来保留状态
        Runnable incrementAndPrint = new Runnable() {
            private int count = 0; // 在闭包内部定义的变量

            @Override
            public void run() {
                // 模拟每次调用时变量的递增
                System.out.println("Lambda 被调用，当前 count 值：" + count++);
            }
        };

        // 创建一个 Consumer<Runnable> 的 Lambda 表达式
        Consumer<Runnable> executeMultipleTimes = runnable -> {
            for (int i = 0; i < 5; i++) {
                runnable.run();
            }
        };

        // 执行多次 Lambda
        executeMultipleTimes.accept(incrementAndPrint);
    }
}