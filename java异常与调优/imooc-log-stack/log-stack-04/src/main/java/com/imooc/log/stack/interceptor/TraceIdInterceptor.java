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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 先清理掉之前的请求 id
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


这段代码展示了一个使用Spring框架实现的拦截器 (`TraceIdInterceptor`)，用于向日志中添加一个唯一的跟踪ID (`traceId`)，以便于跟踪和调试HTTP请求。下面是详细的代码分析以及它所体现的一些调优思想。

### 代码解析

1. **定义拦截器类**:
   - `TraceIdInterceptor` 类实现了 `HandlerInterceptor` 接口，这是Spring MVC提供的用于拦截请求处理的一种机制。
   - 使用 `@Slf4j` 注解引入了Lombok 自动生成的日志变量。
   - 使用 `@Component` 注解将该类声明为Spring管理的组件，以便自动装配。

2. **实现 `preHandle` 方法**:
   - `preHandle` 方法在控制器方法之前被调用，可以用来做一些预处理工作。
   - `HttpServletRequest` 和 `HttpServletResponse` 分别代表了请求和响应对象。
   - `Object handler` 是将要被调用的控制器方法的引用。
   - 方法返回 `boolean` 类型值，如果返回 `true` 则继续处理请求；如果返回 `false` 则停止处理并返回给客户端。

3. **设置跟踪ID (`traceId`)**:
   - `MDC.clear();` 清除上一个请求可能留下的跟踪ID。
   - `String traceId = request.getHeader(FLAG);` 从请求头中获取跟踪ID。
   - 如果请求头中没有跟踪ID，则生成一个新的UUID并将其设置到 `MDC` 中。
   - 如果请求头中有跟踪ID，则直接使用该ID并将其设置到 `MDC` 中。

### 调优思想

1. **日志的关联性**:
   - 通过在每个请求中设置一个唯一的 `traceId`，可以方便地追踪请求的来源和流程。
   - 这对于分布式系统尤为重要，其中请求可能跨越多个服务。

2. **日志的一致性**:
   - 确保每次请求都有一个唯一的跟踪ID，即使请求头中没有提供。
   - 这确保了日志的一致性和完整性。

3. **避免日志污染**:
   - 每个请求开始前清除 `MDC` 中的旧数据，避免不同请求之间的数据混淆。

4. **性能考虑**:
   - 使用 `MDC`（Mapped Diagnostic Context）而不是直接在日志消息中附加跟踪ID，减少了日志记录的开销。
   - `MDC` 是线程局部存储，因此每个线程都有自己独立的 `MDC` 副本，不会影响性能。

5. **代码的简洁性**:
   - 代码简单明了，易于理解和维护。
   - 使用 `StringUtils.isEmpty` 方法检查 `traceId` 是否为空，提高了代码的可读性和健壮性。

6. **可扩展性和复用性**:
   - 通过定义一个独立的拦截器，可以很容易地在多个项目中复用此功能。
   - 拦截器的设计使得可以轻松地添加更多的日志上下文信息。

7. **异常处理**:
   - `preHandle` 方法抛出了 `Exception`，这意味着任何在此方法中发生的异常都将被捕捉并处理，防止它们中断整个请求处理流程。

8. **调试和故障排除**:
   - 通过记录每个请求的唯一 `traceId`，可以更轻松地在日志中追踪问题。
   - 这对于调试分布式系统中的问题特别有用。

9. **性能监控**:
   - 通过跟踪ID可以监控单个请求的处理时间，进而帮助分析性能瓶颈。

10. **避免资源耗尽**:
    - 通过确保每个请求有唯一的跟踪ID，可以避免因日志混乱而导致的资源耗尽问题。

11. **避免耦合**:
    - 将请求ID的设置逻辑放在拦截器中，而不是在业务逻辑中，降低了业务逻辑和日志记录之间的耦合度。

### 总结

这段代码示例通过展示如何在Spring框架中使用拦截器来设置跟踪ID，体现了日志的关联性、一致性、避免日志污染、性能考虑、代码的简洁性、可扩展性和复用性、异常处理、调试和故障排除、性能监控、避免资源耗尽以及避免耦合等调优思想。这些思想对于提高应用程序的可维护性、可读性和性能非常重要。