package com.imooc.log.stack.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class TraceIdInterceptor implements HandlerInterceptor {

    private static final String FLAG = "REQUEST_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 清理之前的请求 id
        MDC.clear();

        String traceId = request.getHeader(FLAG);

        if (StringUtils.isEmpty(traceId)) {
            if (null == MDC.get(FLAG)) {
                MDC.put(FLAG, UUID.randomUUID().toString());
            }
        } else {
            MDC.put(FLAG, traceId);
        }

        return true;
    }
}


这段代码展示了一个基于 Spring 的 Web 应用程序中的拦截器（Interceptor），用于在每个 HTTP 请求进入控制器之前设置一个跟踪 ID (`REQUEST_ID`) 到 Mapped Diagnostic Context (MDC) 中。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义拦截器**:
   - `TraceIdInterceptor` 类实现了 `HandlerInterceptor` 接口，这意味着它可以作为一个拦截器来使用。
   - `@Slf4j` 注解引入了 Lombok 自动生成的日志变量。
   - `@Component` 注解表明这是一个 Spring 管理的组件，Spring 会自动扫描并实例化它。

2. **处理请求前的操作** (`preHandle` 方法):
   - `preHandle` 方法会在每个请求到达控制器方法之前被调用。
   - 在方法中首先调用 `MDC.clear()` 来清除任何遗留的 MDC 数据，以确保每次请求都有一个干净的上下文。
   - 从请求头中获取 `REQUEST_ID`，如果存在则直接使用，如果不存在则生成一个新的 UUID 作为 `REQUEST_ID`。
   - 最后将 `REQUEST_ID` 放入 MDC 中。

### 调优思想

1. **日志链路追踪**:
   - 通过 MDC 可以为每个请求分配一个唯一的标识符。
   - 这有助于追踪请求在系统中的流动路径，特别是对于分布式系统来说非常有用。

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

9. **资源管理**:
   - 使用 `MDC.clear()` 来清理 MDC 中的数据，有助于释放资源，防止内存泄漏。

10. **标准化**:
   - 通过统一在所有请求中设置请求 ID，确保了一致的日志记录标准。
   - 这有助于简化日志分析流程。

### 实际应用场景

在实际应用中，使用这样的拦截器方法适用于以下场景：
- 当需要实现分布式系统的请求追踪时。
- 当需要在日志记录中关联来自不同服务的日志条目时。
- 当需要监控请求的性能时。
- 当需要快速定位问题时。

### 总结

这段代码示例通过展示如何使用 MDC 来记录额外的诊断信息，体现了日志链路追踪、异常定位、性能监控、调试和故障排除、代码简洁性、可扩展性、安全性、多线程支持、资源管理和标准化等调优思想。这对于提高分布式系统的性能和稳定性非常重要。