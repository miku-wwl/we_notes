package com.imooc.log.stack.chapter4;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Logback 的使用</h1>
 * */
public class UseLogback {

    private static final Logger logger = LoggerFactory.getLogger(UseLogback.class);

    /**
     * <h2>支持占位符</h2>
     * */
    private static void log() {
        logger.info("this is slf4j + logback: [{}]", UseLogback.class.getName());
    }

    private static void levelLog() {

        logger.trace("slf4j + logback: [{}]", "trace");
        logger.debug("slf4j + logback: [{}]", "debug");
        logger.info("slf4j + logback: [{}]", "info");
        logger.warn("slf4j + logback: [{}]", "warn");
    }

    /**
     * <h2>打印 logback 的内部状态</h2>
     * */
    private static void printLogbackStatus() {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);
    }

    public static void main(String[] args) {
//        log();

        levelLog();

//        printLogbackStatus();
    }
}


这段代码展示了如何使用 SLF4J 和 Logback 进行日志记录，并展示了 Logback 的一些特性。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **日志框架的选择**:
   - 使用了 SLF4J 作为日志门面，Logback 作为日志实现。

2. **日志记录**:
   - `logger` 是一个 `org.slf4j.Logger` 类型的实例，通过 `LoggerFactory.getLogger(UseLogback.class)` 创建。
   - `log` 方法展示了如何使用占位符来记录日志信息。
   - `levelLog` 方法展示了如何使用不同的日志级别来记录日志信息。

3. **打印 Logback 状态**:
   - `printLogbackStatus` 方法展示了如何打印 Logback 的内部状态，这对于调试和配置 Logback 非常有用。

### 调优思想

1. **日志框架选择**:
   - 选择 SLF4J 作为日志门面，可以轻松地切换底层的日志实现，而无需修改代码。
   - 使用 Logback 作为日志实现，因为它提供了高级的功能和良好的性能。

2. **性能优化**:
   - 使用占位符可以避免在日志级别未启用时进行不必要的字符串拼接。
   - 这有助于提高性能，尤其是在高负载情况下。

3. **日志级别**:
   - 使用不同的日志级别可以更精确地控制哪些日志消息会被记录下来。
   - 这有助于在不同的环境中调整日志输出的详细程度。

4. **日志配置**:
   - 日志框架通常通过外部配置文件来配置日志级别和其他设置。
   - 这样可以在不修改代码的情况下调整日志配置。

5. **可读性和可维护性**:
   - 通过使用占位符和不同的日志级别，可以使日志消息更具可读性。
   - 清晰的日志记录有助于维护和调试代码。

6. **避免日志框架锁定**:
   - 通过使用 SLF4J，可以在不修改代码的情况下更换日志框架。
   - 这有助于避免对某一特定日志框架的依赖。

7. **调试工具**:
   - 通过提供打印 Logback 内部状态的方法，可以方便地调试和验证 Logback 的配置是否正确。
   - 这有助于确保日志框架按照预期工作。

### 实际应用场景

在实际应用中，这种日志记录方式适用于以下场景：
- 当你需要在不同的环境或部署中使用不同的日志框架时。
- 当你需要确保日志记录逻辑不会影响到核心业务逻辑的性能时。
- 当你需要在不修改代码的情况下调整日志级别时。

总结来说，这段代码示例通过展示如何使用 SLF4J 和 Logback 进行日志记录，并展示了 Logback 的一些特性，体现了日志框架选择、性能优化、日志级别、日志配置、可读性和可维护性、避免日志框架锁定以及调试工具等调优思想。这对于提高代码的性能、可读性和可维护性非常重要。