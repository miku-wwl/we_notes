//package com.imooc.log.stack.chapter4;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * <h1>使用 log4j2</h1>
// * */
//public class UseLog4j2 {
//
//    private static final Log logger = LogFactory.getLog(UseLog4j2.class);
//    private static final Logger logger2 = LogManager.getLogger(UseLog4j2.class);
//
//    // 这样做有什么好处呢 ?
//    protected final Log logger3 = LogFactory.getLog(getClass());
//
//    /**
//     * <h2>log4j2 支持占位符, jcl 不支持</h2>
//     * */
//    public static void placeholder() {
//
//        logger2.info("use placeholder, not: [{}]", "abcde");
//    }
//
//    /**
//     * <h2>打印异常栈</h2>
//     * */
//    public static void logWithException() {
//
//        try {
//            System.out.println(Integer.parseInt("a"));
//        } catch (NumberFormatException ex) {
//            logger.error("parse int has some error", ex);
//        }
//    }
//
//    public static void main(String[] args) {
//
//        // 最基本的打印方法
////        logger.error("use jcl + log4j2 to log");
////        logger.info("use jcl + log4j2 to log");
////        logger2.info("use lo4j2 to log");
//
////        placeholder();
//
////        logWithException();
//    }
//}


这段代码展示了如何使用 Apache Log4j 2 来记录日志，并比较了 Log4j 2 与 Apache Commons Logging (JCL) 在日志记录方面的一些差异。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **日志框架的选择**:
   - 使用了 Log4j 2 和 JCL 两种日志框架。
   - Log4j 2 通过 `LogManager.getLogger(UseLog4j2.class)` 创建日志实例。
   - JCL 通过 `LogFactory.getLog(UseLog4j2.class)` 创建日志实例。

2. **日志记录**:
   - `logger` 和 `logger2` 分别是 JCL 和 Log4j 2 的日志实例。
   - `logger3` 是通过 `getClass()` 方法获取当前类的 `Class` 对象，并使用 JCL 创建的日志实例。

3. **占位符支持**:
   - `placeholder` 方法展示了 Log4j 2 支持使用占位符来记录日志，而 JCL 不支持这种方式。
   - 占位符 `{}` 可以在日志消息中使用，并通过后面的参数列表填充。

4. **异常记录**:
   - `logWithException` 方法展示了如何在日志中记录异常信息。
   - 使用 `logger.error` 方法记录错误级别的日志，并通过第二个参数传递异常对象。

### 调优思想

1. **日志框架选择**:
   - 选择适当的日志框架可以提高日志记录的性能和灵活性。
   - Log4j 2 提供了更多的特性，如占位符支持，这有助于提高日志记录的性能。

2. **性能优化**:
   - 使用占位符可以避免在日志级别未启用时进行不必要的字符串拼接。
   - 这有助于提高性能，尤其是在高负载情况下。

3. **异常记录**:
   - 记录异常时，通过将异常对象传递给日志记录方法，可以自动包含异常的堆栈跟踪信息。
   - 这有助于快速定位问题并进行调试。

4. **日志配置**:
   - 日志框架通常通过外部配置文件来配置日志级别和其他设置。
   - 这样可以在不修改代码的情况下调整日志配置。

5. **可读性和可维护性**:
   - 通过使用占位符和异常记录，可以使日志消息更具可读性。
   - 清晰的日志记录有助于维护和调试代码。

6. **避免日志框架锁定**:
   - 通过使用 JCL 和 Log4j 2，可以在不修改代码的情况下更换日志框架。
   - 这有助于避免对某一特定日志框架的依赖。

7. **代码复用**:
   - 通过在类中定义日志实例，可以方便地在不同方法中复用同一个日志实例。
   - 这有助于减少代码重复，并提高代码的可维护性。

### 实际应用场景

在实际应用中，这种日志记录方式适用于以下场景：
- 当你需要在不同的环境或部署中使用不同的日志框架时。
- 当你需要确保日志记录逻辑不会影响到核心业务逻辑的性能时。
- 当你需要在不修改代码的情况下调整日志级别时。

总结来说，这段代码示例通过展示如何使用 Log4j 2 来记录日志，并比较了 Log4j 2 与 JCL 在日志记录方面的一些差异，体现了日志框架选择、性能优化、异常记录、日志配置、可读性和可维护性、避免日志框架锁定以及代码复用等调优思想。这对于提高代码的性能、可读性和可维护性非常重要。