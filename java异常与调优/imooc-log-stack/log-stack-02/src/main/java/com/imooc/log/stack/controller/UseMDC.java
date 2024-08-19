package com.imooc.log.stack.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * <h1>MDC 的使用和源码解析</h1>
 * */
@Slf4j
public class UseMDC {

    // 使用 MDC 之前, 需要先去配置 %X{REQUEST_ID}
    private static final String FLAG = "REQUEST_ID";

    // -----------------------------------------------------------------------------------------------------------------
    // 第一个例子

    private static void mdc01() {

        MDC.put(FLAG, UUID.randomUUID().toString());
        log.info("log in mdc01");
        mdc02();

        log.info("MDC FLAG is: [{}]", MDC.get(FLAG));
        MDC.remove(FLAG);
        log.info("after remove MDC FLAG");

        // 我们没有使用 clear, 但是也能猜出来, 就是清除所有的 key
    }

    private static void mdc02() {

        log.info("log in mdc02");
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 第二个例子, 多线程
    static class MyHandler extends Thread {

        private final String name;

        public MyHandler(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            MDC.put(FLAG, UUID.randomUUID().toString());
            log.info("start to process: [{}]", this.name);

            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }

            log.info("done to process: [{}]", this.name);
            MDC.remove(FLAG);
        }
    }

    private void MultiThreadUseMdc() {

        new MyHandler("imooc").start();
        new MyHandler("qinyi").start();
    }


    public static void main(String[] args) {

//        mdc01();
        new UseMDC().MultiThreadUseMdc();
    }
}


这段代码示例展示了如何在 Java 应用程序中使用 MDC（Mapped Diagnostic Context）来记录额外的诊断信息。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **MDC 的基本使用**:
   - 使用 `MDC.put(FLAG, UUID.randomUUID().toString())` 设置一个名为 `REQUEST_ID` 的键值对。
   - 在日志中使用 `%X{REQUEST_ID}` 来引用这个键值对。
   - 这样可以在日志消息中包含请求 ID，有助于追踪日志来源。

2. **单线程示例** (`mdc01` 和 `mdc02`):
   - 在 `mdc01` 方法中，生成一个随机的请求 ID 并将其放入 MDC。
   - 打印一条日志，此时日志会包含请求 ID。
   - 调用 `mdc02` 方法，该方法也会打印一条日志。
   - 返回到 `mdc01` 方法，再次打印请求 ID。
   - 清除请求 ID，并打印一条日志来确认 MDC 已经清空。

3. **多线程示例** (`MyHandler` 类和 `MultiThreadUseMdc` 方法):
   - 创建了一个继承自 `Thread` 的 `MyHandler` 类。
   - 在 `run` 方法中，为每个线程生成一个唯一的请求 ID 并将其放入 MDC。
   - 打印开始处理的消息，其中包含请求 ID。
   - 模拟处理过程，休眠一段时间。
   - 打印处理完成的消息，同样包含请求 ID。
   - 清除请求 ID。

4. **主函数** (`main` 方法):
   - 创建两个 `MyHandler` 实例，并启动它们。

### 调优思想

1. **日志链路追踪**:
   - 通过 MDC 可以为每个请求或事务分配一个唯一的标识符。
   - 这有助于追踪请求或事务在系统中的流动路径。

2. **异常定位**:
   - 通过在日志中包含请求 ID，可以更容易地定位异常发生的具体请求。
   - 这有助于快速修复问题，减少故障恢复时间。

3. **性能监控**:
   - 通过在日志中包含请求 ID，可以监控每个请求的处理时间。
   - 通过追踪标识，可以收集关于请求处理时间的数据，并进行性能分析。

4. **调试和故障排除**:
   - 通过请求 ID，可以方便地调试和故障排除。
   - 当出现异常时，可以查看与该请求 ID 相关的所有日志条目，以快速定位问题。

5. **代码简洁性**:
   - 通过使用 MDC，提高了代码的可读性和可维护性。
   - 这种做法减少了代码量，使得代码更加简洁明了。

6. **可扩展性**:
   - 通过配置 MDC，可以在不修改业务逻辑的情况下轻松地添加新的请求处理逻辑。
   - 这种做法使得系统更加灵活，易于扩展。

7. **安全性**:
   - 通过记录请求 ID，可以确保请求的成功或失败，并在出现问题时及时采取措施。
   - 这有助于保护系统的安全性。

8. **多线程支持**:
   - 通过在每个线程中设置和清除 MDC 的值，可以确保每个线程的日志信息是独立的。
   - 这有助于避免线程安全问题。

### 实际应用场景

在实际应用中，使用 MDC 的方法适用于以下场景：
- 当需要实现分布式系统的请求追踪时。
- 当需要在日志记录中关联来自不同服务的日志条目时。
- 当需要监控请求的性能时。
- 当需要快速定位问题时。

### 总结

这段代码示例通过展示如何使用 MDC 来记录额外的诊断信息，体现了日志链路追踪、异常定位、性能监控、调试和故障排除、代码简洁性、可扩展性、安全性、多线程支持等调优思想。这对于提高分布式系统的性能和稳定性非常重要。