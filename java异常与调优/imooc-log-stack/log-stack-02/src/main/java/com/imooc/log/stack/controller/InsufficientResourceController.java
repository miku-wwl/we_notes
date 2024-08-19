package com.imooc.log.stack.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>使用线程堆栈日志定位资源不足问题</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/insufficient")
public class InsufficientResourceController {

    /** 自定义线程池, 最好使能够给线程有意义的名字 */
    private final ExecutorService es = Executors.newCachedThreadPool(
            new BasicThreadFactory.Builder().namingPattern("Imooc-Qinyi-%d").build()
    );

    private final StringRedisTemplate redisTemplate;

    public InsufficientResourceController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/{batch}")
    public DeferredResult<String> resource(@PathVariable int batch) {

        DeferredResult<String> result = new DeferredResult<>(10 * 1000L,
                "timeout");
        CompletableFuture[] futures = new CompletableFuture[batch];

        for (int i = 0; i != batch; ++i) {
            futures[i] = CompletableFuture.supplyAsync(this::getValue, es);
        }

        CompletableFuture.allOf(futures).thenRun(() -> result.setResult("success"));

        return result;
    }

    private String getValue() {

        try {
            return redisTemplate.execute((RedisCallback<String>) connection -> {
                sleep(5000);
                return "qinyi-" + connection;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "error";
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

这段代码示例展示了如何在 Spring Boot 应用程序中使用线程堆栈日志来定位资源不足的问题。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **自定义线程池**:
   - 使用 `Executors.newCachedThreadPool` 创建了一个缓存线程池。
   - 使用 `BasicThreadFactory` 来创建线程，并给线程命名，这有助于在日志中更容易地识别线程。

2. **使用 `DeferredResult`**:
   - `resource` 方法接收一个 `batch` 参数，表示要异步执行的任务数量。
   - 创建了一个 `DeferredResult<String>` 对象，用于异步处理请求。
   - 创建了一个 `CompletableFuture` 数组，每个元素代表一个异步任务。
   - 每个任务都是通过调用 `supplyAsync` 方法异步执行 `getValue` 方法。
   - 使用 `CompletableFuture.allOf` 方法来等待所有任务完成。
   - 一旦所有任务完成，设置 `DeferredResult` 的结果为 "success"。

3. **从 Redis 获取值**:
   - `getValue` 方法是一个异步任务，它通过 `StringRedisTemplate` 从 Redis 获取一个值。
   - 使用 `execute` 方法执行一个 Redis 操作，这里模拟了一个耗时操作。
   - 通过 `sleep(5000)` 方法模拟了一个较长的延迟，这会导致线程阻塞。

4. **异常处理**:
   - 如果在 Redis 操作过程中发生异常，捕获异常并返回 "error"。

### 调优思想

1. **资源监控**:
   - 通过使用线程堆栈日志，可以监控应用程序中线程的使用情况。
   - 当发现大量线程处于等待或阻塞状态时，可以定位资源不足的问题。

2. **性能优化**:
   - 通过优化异步任务的执行，可以提高应用程序的性能。
   - 例如，通过减少不必要的延迟操作或优化 Redis 操作，可以减少线程的等待时间。

3. **异常定位**:
   - 通过线程堆栈日志可以辅助定位异常发生的位置。
   - 如果一个线程长时间处于等待状态，可能是因为它在等待某个资源或锁。

4. **线程状态分析**:
   - 了解线程状态可以帮助分析线程的行为。
   - 例如，如果发现线程频繁地在 `RUNNABLE` 和 `TIMED_WAITING` 之间切换，可能意味着存在 I/O 阻塞或等待事件。

5. **调试和故障排除**:
   - 线程堆栈信息对于调试多线程应用程序中的问题非常有用。
   - 通过观察线程的状态，可以快速定位问题，并理解线程间的交互。

6. **代码简洁性**:
   - 使用 `CompletableFuture` 和 `DeferredResult` 提供了简洁的方式来进行异步处理。
   - 这种做法减少了代码量，提高了代码的可读性和可维护性。

7. **可扩展性**:
   - 通过配置自定义线程池，可以根据需求调整线程的数量。
   - 这种做法使得系统更加灵活，易于扩展。

8. **避免资源耗尽**:
   - 通过合理控制线程数量和线程状态，可以避免资源耗尽的问题。
   - 如果线程池中的线程长时间处于阻塞状态，可能会导致线程池资源耗尽。

9. **避免死锁**:
   - 通过避免线程长期处于等待状态，可以减少死锁的风险。
   - 如果线程等待某个条件被满足的时间过长，可能会与其他线程形成等待链，从而导致死锁。

### 实际应用场景

在实际应用中，使用线程堆栈日志来定位资源不足问题的方法适用于以下场景：
- 当需要监控应用程序的性能和状态时。
- 当需要调试多线程应用程序中的问题时。
- 当需要优化应用程序的性能时。

总结来说，这段代码示例通过展示如何使用线程堆栈日志来定位资源不足的问题，体现了资源监控、性能优化、异常定位、线程状态分析、调试和故障排除、代码简洁性、可扩展性、避免资源耗尽以及避免死锁等调优思想。这对于提高应用程序的性能和稳定性非常重要。

### 示例解释

1. **模拟资源不足**:
   - 通过模拟一个长时间运行的操作（如 `sleep(5000)`），可以模拟资源不足的情况。
   - 如果大量这样的任务同时执行，可能会导致线程池资源耗尽。

2. **异步处理**:
   - 使用 `CompletableFuture` 和 `supplyAsync` 方法来异步执行任务。
   - 这样可以避免阻塞主线程，并允许其他任务并行执行。

3. **超时处理**:
   - 使用 `DeferredResult` 设置超时时间为 10 秒。
   - 如果所有任务未能在 10 秒内完成，`DeferredResult` 将返回 "timeout"。

4. **异常处理**:
   - 在 `getValue` 方法中，捕获任何可能发生的异常，并返回 "error"。
   - 这有助于确保即使在异常情况下，也能提供一个响应。

### 总结

这段代码示例通过展示如何模拟资源不足的情况并使用线程堆栈日志来定位问题，体现了资源监控、性能优化、异常定位、线程状态分析、调试和故障排除、代码简洁性、可扩展性、避免资源耗尽以及避免死锁等调优思想。这对于提高应用程序的性能和稳定性非常重要。