package com.imooc.log.stack.chapter4;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * <h1>动态构造 Logger 对象</h1>
 * */
@SuppressWarnings("all")
public class LogbackHolder {

    /**
     * <h2>根据名称获取 logger 实例</h2>
     * */
    public static Logger getLogger(String name) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 如果没有创建 logger
        if (loggerContext.exists(name) == null) {
            // 自己动态构造 logger 对象
            return buildLogger(name);
        }

        return loggerContext.getLogger(name);
    }

    /**
     * <h2>动态构造 logger 实例</h2>
     * */
    private static Logger buildLogger(String name) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(name);

        // 配置 rollingFileAppender
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setName(name);
        rollingFileAppender.setContext(loggerContext);

        // 配置 rollingPolicy
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setFileNamePattern("/tmp/log/" + name + ".%d{yyyyMM}.log");
        rollingPolicy.setParent(rollingFileAppender);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.start();

        // 配置 encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setCharset(StandardCharsets.UTF_8);
        encoder.setPattern("%msg%n");
        encoder.setContext(loggerContext);
        encoder.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.start();

        // 配置 logger
        logger.addAppender(rollingFileAppender);
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);

        return logger;
    }

    public static void main(String[] args) {

        getLogger("qinyi").info("imooc qinyi use logback...");
    }
}


这段代码展示了如何动态地创建和配置 Logback 的 `Logger` 实例，并设置滚动文件日志记录器。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **获取或创建 `Logger` 实例**:
   - 使用 `getLogger` 方法根据名称获取或创建 `Logger` 实例。
   - 如果已经存在指定名称的 `Logger`，则直接返回该实例；如果不存在，则创建新的 `Logger`。

2. **动态配置 `Logger`**:
   - `buildLogger` 方法负责创建和配置一个新的 `Logger` 实例。
   - 创建 `RollingFileAppender` 和 `TimeBasedRollingPolicy` 用于滚动文件日志记录。
   - 创建 `PatternLayoutEncoder` 用于定义日志输出的格式。
   - 设置 `RollingFileAppender` 的属性，包括文件名模式、编码器和滚动策略。
   - 最后启动各个组件，并将其添加到 `Logger` 实例中。

3. **日志记录**:
   - 在 `main` 方法中，通过调用 `getLogger` 方法获取名为 "qinyi" 的 `Logger` 实例，并使用 `info` 方法记录一条日志信息。

### 调优思想

1. **动态配置**:
   - 通过动态配置 `Logger` 实例，可以在运行时根据需要创建新的日志记录器。
   - 这有助于在不需要重新编译或重启应用的情况下调整日志配置。

2. **资源管理**:
   - 通过手动创建和配置 `RollingFileAppender`、`TimeBasedRollingPolicy` 和 `PatternLayoutEncoder`，可以更精确地控制日志的存储位置、文件命名规则和日志格式。
   - 这有助于优化磁盘空间的使用，并使日志输出更具可读性。

3. **避免重复配置**:
   - 通过检查是否存在同名的 `Logger` 实例，避免重复创建和配置相同的 `Logger`。
   - 这有助于减少资源消耗，并避免不必要的日志输出。

4. **可扩展性和灵活性**:
   - 动态配置 `Logger` 实例提供了更高的灵活性，可以方便地添加新的日志记录器或调整现有日志记录器的行为。
   - 这对于大型项目或需要频繁调整日志配置的应用非常有用。

5. **性能优化**:
   - 通过设置 `setAdditive(false)`，可以避免将日志消息发送到默认的日志记录器，从而减少不必要的日志输出，提高性能。

### 实际应用场景

在实际应用中，这种动态创建和配置 `Logger` 的方法适用于以下场景：
- 当你需要根据不同的需求动态创建不同的日志记录器时。
- 当你需要在运行时调整日志配置而无需重启应用时。
- 当你需要优化日志文件的存储方式和格式时。

总结来说，这段代码示例通过展示如何动态创建和配置 Logback 的 `Logger` 实例，体现了动态配置、资源管理、避免重复配置、可扩展性和灵活性以及性能优化等调优思想。这对于提高应用的可维护性、灵活性和性能非常重要。