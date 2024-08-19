package com.imooc.log.stack.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>广义的调试</h1>
 * */
@Slf4j
public class CodeDebug {

    private int sum(int x, int y) {
        System.out.println("x, y: " + x + ", " + y);
        return x + y;
    }

    private String joint(String x, String y) {

        assert null != x;
        assert null != y;

        return x + y;
    }

    private int sum2(int x, int y) {

        log.debug("args: [{}], [{}]", x, y);
        return x + y;
    }
}


这段代码展示了如何在 Java 中使用不同的技术来进行调试和日志记录。以下是代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **方法 `sum(int x, int y)`**:
   - 这个方法接收两个整数参数，并返回它们的和。
   - 使用 `System.out.println` 打印参数值，这通常是一种简单的调试手段，用于观察传入方法的参数值。

2. **方法 `joint(String x, String y)`**:
   - 这个方法接收两个字符串参数，并返回它们连接后的结果。
   - 使用 `assert` 关键字进行断言检查，确保传入的字符串都不为 `null`。如果任意一个参数为 `null`，则会抛出 `AssertionError`。

3. **方法 `sum2(int x, int y)`**:
   - 这个方法也接收两个整数参数，并返回它们的和。
   - 使用 Lombok 自动注入的 `Logger` 对象来记录参数值，这里使用的是 `debug` 级别的日志。
   - 与 `System.out.println` 不同，`log.debug` 允许更细粒度地控制日志输出，可以根据需要开启或关闭 `DEBUG` 级别的日志记录。

### 调优思想

1. **代码简洁性**:
   - 使用 Lombok 的 `@Slf4j` 注解自动生成 `Logger`，减少模板代码。

2. **性能优化**:
   - 使用 `log.debug` 替代 `System.out.println` 可以避免不必要的输出，特别是在生产环境中，可以关闭 `DEBUG` 级别的日志记录。

3. **可读性和可维护性**:
   - 使用 `assert` 断言来检查参数的有效性，有助于确保代码的健壮性。
   - 使用 `log.debug` 记录调试信息，使得调试信息和生产日志分离，易于管理。

4. **避免日志框架锁定**:
   - 使用 SLF4J 作为日志门面，可以在不修改代码的情况下更换日志框架。

5. **日志配置**:
   - 日志级别可以通过外部配置文件来调整，这意味着可以在不影响代码的情况下改变日志行为。

### 实际应用场景

在实际应用中，这种使用多种技术进行调试和日志记录的方法适用于以下场景：
- 当需要在开发阶段查看变量的实时状态时，可以使用 `System.out.println`。
- 当需要确保某些条件始终满足时，可以使用 `assert` 断言。
- 当需要记录详细的调试信息时，可以使用 `log.debug`，并且可以根据需要调整日志级别。

总结来说，这段代码示例通过展示如何使用 `System.out.println`、`assert` 断言和 Lombok 的 `@Slf4j` 注解来简化日志记录的过程，体现了代码简洁性、性能优化、可读性和可维护性、避免日志框架锁定以及日志配置等调优思想。这对于提高代码的性能、可读性和可维护性非常重要。