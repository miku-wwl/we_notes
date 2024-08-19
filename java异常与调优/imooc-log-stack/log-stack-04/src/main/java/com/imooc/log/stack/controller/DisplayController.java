package com.imooc.log.stack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/display")
public class DisplayController {

    /**
     * 127.0.0.1:9527/api/display/controller?name=&requestId=
     * requestId 参数化并不好, 这样业务和日志耦合在一起
     * */
    @GetMapping("/controller")
    public void print(@RequestParam("name") String name,
                      @RequestParam(name = "requestId", required = false) String requestId) {

        log.info("coming in 9527 with args: [{}]", name);
    }
}


这段代码示例展示了一个简单的 Spring Boot 控制器 (`DisplayController`)，该控制器接收 HTTP GET 请求并记录日志。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义控制器类**:
   - `DisplayController` 类使用 `@RestController` 注解标记，表示这是一个 RESTful 控制器。
   - `@Slf4j` 注解引入了 Lombok 自动生成的日志变量。
   - `@RequestMapping("/display")` 定义了控制器的基础路径。

2. **定义处理方法** (`print` 方法):
   - `@GetMapping("/controller")` 定义了处理 GET 请求的路径。
   - 方法接收两个参数：`name` 和 `requestId`。
   - `@RequestParam("name") String name` 从请求的查询参数中获取 `name`。
   - `@RequestParam(name = "requestId", required = false) String requestId` 从请求的查询参数中获取 `requestId`，并且它是可选的。
   - `log.info("coming in 9527 with args: [{}]", name);` 打印一条包含 `name` 参数的日志。

### 调优思想

1. **日志的合理性**:
   - 通过在控制器中记录日志，可以追踪请求的处理过程。
   - 使用 `info` 级别的日志记录正常的请求处理情况。

2. **参数化日志**:
   - 通过使用占位符 `[{}]` 并将参数传递给日志方法，可以避免字符串拼接带来的性能开销。
   - 这种做法提高了日志记录的效率。

3. **日志的必要性**:
   - 只记录必要的信息。
   - 例如，只记录 `name` 参数而不记录 `requestId`，因为 `requestId` 可能已经在其他地方被记录。

4. **日志的正确性**:
   - 确保日志提供足够的信息来帮助解决问题。
   - 例如，记录请求参数可以帮助调试和理解请求的内容。

5. **性能监控**:
   - 通过日志可以监控应用程序的性能。
   - 例如，记录请求的处理时间可以帮助评估系统的响应速度。

6. **异常定位**:
   - 通过日志可以辅助定位异常发生的位置。
   - 例如，记录异常的原因可以帮助快速找到问题所在。

7. **调试和故障排除**:
   - 日志信息对于调试多线程应用程序中的问题非常有用。
   - 通过观察日志，可以快速定位问题，并理解程序的执行流程。

8. **代码简洁性**:
   - 使用简洁的方法来记录日志，这有助于提高代码的可读性。
   - 例如，使用 `log.info` 方法来记录不同类型的信息。

9. **代码可读性**:
   - 通过为每种情况创建明确的日志记录语句，可以提高代码的组织性和可读性。
   - 这使得其他开发者更容易理解每个日志语句的目的和作用。

10. **避免资源耗尽**:
    - 通过合理控制日志记录的内容和频率，可以避免资源耗尽的问题。
    - 如果日志记录过于频繁或记录的信息量过大，可能会导致磁盘空间迅速耗尽。

11. **避免性能瓶颈**:
    - 通过减少不必要的日志记录，可以避免成为性能瓶颈。
    - 例如，避免在循环中频繁记录日志，这可能会导致性能下降。

12. **避免耦合**:
    - 在这个示例中，`requestId` 作为查询参数传递给控制器，这可能会导致业务逻辑和日志记录之间的耦合。
    - 更好的做法是通过全局拦截器或过滤器来处理 `requestId` 的设置和传播，从而避免业务逻辑和日志记录之间的直接耦合。

### 实际应用场景

在实际应用中，使用日志来记录请求处理的情况适用于以下场景：
- 当需要监控应用程序的性能和状态时。
- 当需要调试多线程应用程序中的问题时。
- 当需要优化应用程序的性能时。

### 总结

这段代码示例通过展示如何在控制器中记录日志，体现了日志的合理性、正确性、必要性、性能监控、异常定位、调试和故障排除、代码简洁性、代码可读性、避免资源耗尽以及避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。同时，通过避免耦合的做法，可以进一步优化代码结构。