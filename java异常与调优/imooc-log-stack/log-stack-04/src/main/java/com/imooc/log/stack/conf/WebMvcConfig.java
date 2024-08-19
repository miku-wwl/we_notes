package com.imooc.log.stack.conf;

import com.imooc.log.stack.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**").order(0);
    }
}


这段代码示例展示了如何在 Spring Boot 应用程序中配置一个全局的拦截器 (`TraceIdInterceptor`)，该拦截器用于在每个 HTTP 请求进入控制器之前设置一个跟踪 ID (`REQUEST_ID`) 到 Mapped Diagnostic Context (MDC) 中。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义配置类**:
   - `WebMvcConfig` 类继承自 `WebMvcConfigurationSupport`，这是一个 Spring MVC 提供的配置支持类。
   - `@Configuration` 注解表明这是一个 Spring 配置类。

2. **配置拦截器** (`addInterceptors` 方法):
   - 重写了 `addInterceptors` 方法，该方法允许注册自定义的拦截器。
   - 使用 `registry.addInterceptor(new TraceIdInterceptor())` 来添加 `TraceIdInterceptor` 拦截器。
   - `addPathPatterns("/**")` 表示此拦截器应用于所有路径。
   - `order(0)` 设置了拦截器的顺序，数值越小优先级越高。

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
   - 通过使用 Spring 的配置支持类来配置拦截器，提高了代码的可读性和可维护性。
   - 这种做法减少了代码量，使得代码更加简洁明了。

6. **可扩展性**:
   - 通过配置拦截器，可以在不修改业务逻辑的情况下轻松地添加新的请求处理逻辑。
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

在实际应用中，使用这样的拦截器配置方法适用于以下场景：
- 当需要实现分布式系统的请求追踪时。
- 当需要在日志记录中关联来自不同服务的日志条目时。
- 当需要监控请求的性能时。
- 当需要快速定位问题时。

### 总结

这段代码示例通过展示如何配置一个全局的 `TraceIdInterceptor` 拦截器，体现了日志链路追踪、异常定位、性能监控、调试和故障排除、代码简洁性、可扩展性、安全性、多线程支持、资源管理和标准化等调优思想。这对于提高分布式系统的性能和稳定性非常重要。