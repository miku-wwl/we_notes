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


这段 Java 代码示例展示了如何使用不同类型的断点来调试程序。断点是调试程序的一个重要工具，它允许开发者暂停程序的执行以便检查程序的状态。下面是对代码的详细解析及其中体现的调优思想。

### 代码解析

1. **行断点**:
   - `lineBreakPoint` 方法用于演示行断点的使用。
   - 行断点通常设置在一个特定的代码行上，当程序执行到这一行时就会暂停。

2. **临时断点**:
   - 临时断点与行断点类似，但在程序第一次命中后就会自动移除。
   - 代码中并未具体实现，只是提到了这个概念。

3. **属性断点**:
   - `FieldWatchPoint` 类展示了属性断点的使用场景。
   - 属性断点可以监视一个对象的属性变化，当该属性的值发生变化时，程序会暂停。
   - 在 `main` 方法中创建了一个 `FieldWatchPoint` 对象，并尝试改变其 `name` 属性。

4. **方法断点**:
   - `printImoocPrefix` 方法用于演示方法断点的使用。
   - 方法断点会在方法被调用时暂停程序执行。

5. **异常断点**:
   - `exceptionBreakPoint` 方法用于演示异常断点的使用。
   - 异常断点会在抛出特定类型的异常时暂停程序执行。
   - 在 `main` 方法中尝试触发一个自定义异常 `CustomException`。

### 调优思想

1. **调试与问题定位**:
   - 使用断点可以帮助快速定位问题所在的位置。
   - 通过观察程序状态的变化，可以更好地理解程序的行为和流程。

2. **逐步调试**:
   - 断点允许开发者逐步执行程序，有助于理解复杂的逻辑流程。
   - 通过逐步执行，可以确保每一步的输出符合预期。

3. **变量监视**:
   - 属性断点可以用来监视对象的状态变化。
   - 通过监视关键变量的状态变化，可以更好地理解程序的运行情况。

4. **异常处理**:
   - 异常断点帮助识别异常发生的时机和位置。
   - 通过异常断点，可以分析异常产生的原因，并进行相应的处理或改进代码。

5. **性能优化**:
   - 通过调试找到性能瓶颈，例如循环中的无效操作或不必要的计算。
   - 使用断点可以帮助理解程序在某些特定路径上的行为，从而找到优化的机会。

6. **代码质量提升**:
   - 调试过程中可以发现代码中不易察觉的逻辑错误或设计缺陷。
   - 通过调试和修正这些问题，可以提高代码的整体质量和可维护性。

### 实际应用场景

在实际开发中，断点是调试程序不可或缺的工具之一。以下是使用断点进行调试的一些建议：

1. **合理使用断点**:
   - 设置断点时应尽量精确，避免在不必要的位置设置断点，以减少调试时间。
   - 利用临时断点来减少重复设置相同断点的工作量。

2. **监视变量**:
   - 使用属性断点来监视对象状态的变化，特别是在多线程环境中，这有助于理解并发行为。

3. **异常调试**:
   - 在调试异常时，除了设置异常断点外，还可以结合日志记录和单元测试来进一步定位问题。

4. **性能分析**:
   - 结合性能分析工具，如 Profiler，来辅助调试性能问题。
   - 使用断点来检查性能关键部分的执行情况。

5. **单元测试**:
   - 在进行调试的同时，编写针对问题区域的单元测试，以确保修复的 bug 不会在未来再次出现。

总结来说，这段代码示例通过展示不同的断点类型及其用法，体现了调试与问题定位、逐步调试、变量监视、异常处理、性能优化以及代码质量提升等调优思想。这些思想对于提高软件的质量和效率至关重要。