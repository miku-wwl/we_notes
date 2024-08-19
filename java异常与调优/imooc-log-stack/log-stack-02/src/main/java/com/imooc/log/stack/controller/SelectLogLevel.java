package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * <h1>选择合适的日志打印级别</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/select")
public class SelectLogLevel {

    /**
     * <h2>选择日志级别</h2>
     * */
    @PostMapping("/log/level")
    public Imoocer selectLogLevel(Imoocer imoocer) {

        // 1. 更加具体的调试信息, 例如调用了什么方法, 参数是什么, 可以使用 trace
        log.trace("call printSomething, with args: [{}], [{}]", null, null);
        printSomething(null, null);

        // 2. 在项目开发阶段, 调试程序的正确性, 可以使用 debug
        log.debug("coming in selectLogLevel with args: [{}]", imoocer);

        // 3. 正常的业务执行流程、系统的启动/关闭、需要做的审计等等都可以使用 info
        if (imoocer.getAge() > 19) {
            // 将来可以用于统计 age 大于 19 的请求
            log.info("imoocer age > 19: [{}]", imoocer.getAge());
        }

        // 4. 不是错误, 不会影响程序的正常执行, 但是并不建议这样做, 可以使用 warn
        try {
            callRemoteSystem();
        } catch (Exception ex) {
            log.warn("call remote system error: [{}]", System.currentTimeMillis());
        }

        // 5. 程序出现了某种错误, 需要介入处理
        try {
            // 读取 classpath 下的 error.txt 文件
            org.springframework.util.ResourceUtils.getFile("classpath:error.txt");
        } catch (FileNotFoundException ex) {
            log.error("error.txt is not exist");
            return null;
        }

        return imoocer;
    }

    private void printSomething(String x, String y) {
        // ...
    }

    private void callRemoteSystem() {
        // ...
    }
}


这段代码示例展示了如何根据不同的情况选择合适的日志级别来记录日志。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **使用 `trace` 级别的日志**:
   - `log.trace("call printSomething, with args: [{}], [{}]", null, null);`
   - 使用 `trace` 级别记录调用 `printSomething` 方法的信息。
   - 通常用于记录非常详细的调试信息，这些信息对于跟踪程序执行路径很有帮助。

2. **使用 `debug` 级别的日志**:
   - `log.debug("coming in selectLogLevel with args: [{}]", imoocer);`
   - 使用 `debug` 级别记录进入 `selectLogLevel` 方法的信息。
   - 通常用于记录程序执行过程中的详细信息，主要用于开发和测试阶段。

3. **使用 `info` 级别的日志**:
   - `if (imoocer.getAge() > 19) { log.info("imoocer age > 19: [{}]", imoocer.getAge()); }`
   - 使用 `info` 级别记录年龄大于 19 的 Imoocer 信息。
   - 通常用于记录程序运行时的重要信息，如业务流程、系统启动/关闭等。

4. **使用 `warn` 级别的日志**:
   - `try { callRemoteSystem(); } catch (Exception ex) { log.warn("call remote system error: [{}]", System.currentTimeMillis()); }`
   - 使用 `warn` 级别记录调用远程系统时的警告信息。
   - 通常用于记录虽然不影响程序继续运行，但可能需要关注的情况。

5. **使用 `error` 级别的日志**:
   - `try { ... } catch (FileNotFoundException ex) { log.error("error.txt is not exist"); return null; }`
   - 使用 `error` 级别记录找不到文件的错误信息。
   - 通常用于记录程序中出现的错误，这些错误需要立即解决。

### 调优思想

1. **日志的合理性**:
   - 根据不同的情况选择合适的日志级别。
   - 例如，使用 `trace` 级别记录详细的调试信息，使用 `error` 级别记录严重的错误。

2. **日志的正确性**:
   - 确保日志提供足够的信息来帮助解决问题。
   - 例如，记录异常的原因和上下文信息，以便快速定位问题。

3. **日志的必要性**:
   - 只记录必要的信息。
   - 避免记录过多的 `debug` 级别日志，以免影响性能。

4. **性能监控**:
   - 通过日志可以监控应用程序的性能。
   - 例如，记录 HTTP 请求的响应时间可以帮助评估网络延迟。

5. **异常定位**:
   - 通过日志可以辅助定位异常发生的位置。
   - 例如，记录异常的原因可以帮助快速找到问题所在。

6. **调试和故障排除**:
   - 日志信息对于调试多线程应用程序中的问题非常有用。
   - 通过观察日志，可以快速定位问题，并理解程序的执行流程。

7. **代码简洁性**:
   - 使用简洁的方法来记录日志，这有助于提高代码的可读性。
   - 例如，使用 `log.trace`, `log.debug`, `log.info`, `log.warn`, 和 `log.error` 方法来记录不同类型的信息。

8. **代码可读性**:
   - 通过为每种情况创建明确的日志记录语句，可以提高代码的组织性和可读性。
   - 这使得其他开发者更容易理解每个日志语句的目的和作用。

9. **避免资源耗尽**:
   - 通过合理控制日志记录的内容和频率，可以避免资源耗尽的问题。
   - 如果日志记录过于频繁或记录的信息量过大，可能会导致磁盘空间迅速耗尽。

10. **避免性能瓶颈**:
    - 通过减少不必要的日志记录，可以避免成为性能瓶颈。
    - 例如，避免在循环中频繁记录日志，这可能会导致性能下降。

### 实际应用场景

在实际应用中，选择合适的日志级别的方法适用于以下场景：
- 当需要监控应用程序的性能和状态时。
- 当需要调试多线程应用程序中的问题时。
- 当需要优化应用程序的性能时。

总结来说，这段代码示例通过展示如何根据不同的情况选择合适的日志级别来记录日志，体现了日志的合理性、正确性、必要性、性能监控、异常定位、调试和故障排除、代码简洁性、代码可读性、避免资源耗尽以及避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。