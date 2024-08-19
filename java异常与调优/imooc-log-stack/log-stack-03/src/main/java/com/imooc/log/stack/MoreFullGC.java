package com.imooc.log.stack;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h1>频繁的 Full GC</h1>
 * -Xms20M -Xmx20M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * */
@SuppressWarnings("all")
public class MoreFullGC {

    @Data
    private static class Imoocer {

        private String name = "qinyi";
        private int age = 19;
        private String gender = "male";
        private LocalDate birthday = LocalDate.MAX;

        public void func() {
            //
        }
    }

    /** 线程池 */
    private static final ScheduledThreadPoolExecutor executor =
            new ScheduledThreadPoolExecutor(50,
                    new ThreadPoolExecutor.DiscardOldestPolicy());

    private static void processImoocers(List<Imoocer> imoocers) {
        imoocers.forEach(i -> executor.scheduleWithFixedDelay(
                i::func, 2, 3, TimeUnit.SECONDS
        ));
    }

    private static List<Imoocer> getAllImoocer(int count) {

        List<Imoocer> imoocers = new ArrayList<>(count);

        for (int i = 0; i != count; ++i) {
            imoocers.add(new Imoocer());
        }

        return imoocers;
    }

    public static void main(String[] args) throws InterruptedException {

        executor.setMaximumPoolSize(50);

        while (true) {
            processImoocers(getAllImoocer(100));
            Thread.sleep(100);
        }
    }
}

这段代码示例展示了一个可能导致频繁 Full GC 的场景。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义 `Imoocer` 类**:
   - `Imoocer` 类是一个简单的 POJO 类，包含了姓名、年龄、性别和生日等属性。
   - 定义了一个 `func` 方法，该方法在本示例中为空。

2. **线程池配置**:
   - 使用 `ScheduledThreadPoolExecutor` 创建了一个线程池，线程池最大容量为 50。
   - `ThreadPoolExecutor.DiscardOldestPolicy()` 表示当线程池任务队列已满时，丢弃最旧的任务。

3. **处理 Imoocer 列表** (`processImoocers` 方法):
   - 遍历传入的 `Imoocer` 列表，并为每个 `Imoocer` 安排一个固定延迟的任务。
   - 任务的初始延迟为 2 秒，之后每隔 3 秒重复执行一次。

4. **获取所有 Imoocer** (`getAllImoocer` 方法):
   - 创建一个指定数量的 `Imoocer` 对象列表。

5. **主函数** (`main` 方法):
   - 设置线程池的最大容量为 50。
   - 无限循环中不断调用 `processImoocers` 方法处理 `Imoocer` 列表，并在每次处理后等待 100 毫秒。

### 调优思想

1. **内存管理**:
   - 由于 `Imoocer` 对象被不断地创建并添加到线程池的任务中，但没有显式地回收，这可能导致 Young Generation 快速填满，进而触发频繁的 Full GC。
   - 这种情况下的调优可能涉及调整 JVM 参数来优化内存分配策略，或者改变程序逻辑以减少对象创建。

2. **线程池配置**:
   - 使用 `ScheduledThreadPoolExecutor` 可能导致大量的线程被创建和销毁，增加内存压力。
   - 考虑使用固定大小的线程池来减少线程的创建和销毁开销。

3. **任务调度**:
   - 任务被安排以固定的间隔重复执行，这可能导致任务积压，增加内存使用。
   - 调整任务的执行间隔或者限制任务的并发数可以减少内存消耗。

4. **对象重用**:
   - 由于对象被频繁创建而没有被重用，这增加了 GC 的负担。
   - 采用对象池技术可以减少对象创建的数量，从而降低 GC 的频率。

5. **性能监控**:
   - 通过观察 GC 的行为，可以发现性能瓶颈。
   - 使用 `-XX:+PrintGCDetails` 和 `-XX:+PrintGCTimeStamps` 等 JVM 参数可以帮助监控 GC 的行为。

6. **异常定位**:
   - 通过观察 GC 的日志，可以发现内存泄漏或其他内存相关的问题。
   - 这有助于快速定位问题并采取相应的措施。

7. **代码简洁性**:
   - 通过简化对象的创建和管理逻辑，可以减少代码的复杂度。
   - 例如，使用对象池技术可以简化对象管理的代码。

8. **可扩展性**:
   - 通过调整线程池的大小和任务的执行策略，可以轻松地扩展应用程序。
   - 这种做法使得系统更加灵活，易于扩展。

9. **资源管理**:
   - 通过合理管理资源，例如减少不必要的对象创建，可以避免资源耗尽的问题。
   - 例如，通过对象池来管理 `Imoocer` 对象。

10. **避免性能瓶颈**:
    - 通过减少不必要的线程创建和销毁，可以避免成为性能瓶颈。
    - 例如，使用固定大小的线程池可以减少线程切换的开销。

### 实际应用场景

在实际应用中，频繁 Full GC 的问题通常出现在以下场景：
- 当应用程序频繁创建大量临时对象时。
- 当内存分配策略不适合应用程序的工作负载时。
- 当线程池配置不当导致大量线程被创建和销毁时。

### 总结

这段代码示例通过展示如何创建一个可能导致频繁 Full GC 的场景，体现了内存管理、线程池配置、任务调度、对象重用、性能监控、异常定位、代码简洁性、可扩展性、资源管理和避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。
