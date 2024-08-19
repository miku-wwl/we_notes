package com.imooc.log.stack;

/**
 * <h1>频繁的 Minor GC 和 Major GC</h1>
 * -XX:NewSize=5M -XX:MaxNewSize=5M -XX:InitialHeapSize=10M -XX:MaxHeapSize=10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * -XX:NewSize=50M -XX:MaxNewSize=50M -XX:InitialHeapSize=100M -XX:MaxHeapSize=100M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 * 年轻代 = Eden + 2 Sur(From + To)
 * Eden 4MB
 * 2Sur 0.5 + 0.5 = 1MB
 * */
@SuppressWarnings("all")
public class MoreMinorGC {

    private static void minorGC() throws InterruptedException {

        byte[] x = new byte[1024 * 1024];   // 在 Eden 区域放入一个 1MB 的对象
        x = new byte[1024 * 1024];
        x = new byte[1024 * 1024];  // 会导致前两个 1MB 的对象成为垃圾对象
        x = null;   // 将之前的三个 1MB 的对象都变成垃圾对象

        // 这句代码就会触发年轻代的 Young GC
        byte[] y = new byte[2 * 1024 * 1024];   // 在 Eden 区中分配一个 2MB 的对象

        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            minorGC();
        }
    }
}


这段代码示例展示了如何在 Java 应用程序中触发频繁的 Minor GC（也称为 Young GC）。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义 `minorGC` 方法**:
   - 在方法内部，连续创建三个 1 MB 大小的字节数组对象 `x`。
   - 每次创建新数组时，都会将前一个数组赋值给 `null`，使其成为垃圾对象。
   - 最后创建一个 2 MB 大小的字节数组 `y`。
   - 由于 Eden 区的大小设置为 4 MB，并且 `y` 占用了 2 MB，这将导致 Eden 区填满。
   - 这将触发 Minor GC，以清理之前创建的垃圾对象。

2. **主函数 (`main` 方法)**:
   - 在一个无限循环中调用 `minorGC` 方法，这将导致 Minor GC 频繁发生。

### 调优思想

1. **内存管理**:
   - 通过调整年轻代的大小和 Survivor Ratio，可以控制 Minor GC 的频率。
   - 在这个示例中，Eden 区的大小设置为 4 MB，而 Survivor Ratio 设置为 8，意味着 Survivor 空间占年轻代总空间的 1/9。

2. **对象生命周期**:
   - 通过创建短生命周期的对象，可以触发频繁的 Minor GC。
   - 在示例中，通过将对象设置为 `null`，可以确保它们很快成为垃圾对象。

3. **性能监控**:
   - 通过观察 GC 的行为，可以发现性能瓶颈。
   - 使用 `-XX:+PrintGCDetails` 和 `-XX:+PrintGCTimeStamps` 等 JVM 参数可以帮助监控 GC 的行为。

4. **异常定位**:
   - 通过观察 GC 的日志，可以发现内存泄漏或其他内存相关的问题。
   - 这有助于快速定位问题并采取相应的措施。

5. **代码简洁性**:
   - 通过简化对象的创建和管理逻辑，可以减少代码的复杂度。
   - 例如，在这个示例中，通过简单地创建和丢弃对象，可以清楚地展示 Minor GC 的触发条件。

6. **可扩展性**:
   - 通过调整 JVM 参数，可以轻松地扩展应用程序的内存配置。
   - 这种做法使得系统更加灵活，易于扩展。

7. **资源管理**:
   - 通过合理管理资源，例如减少不必要的对象创建，可以避免资源耗尽的问题。
   - 例如，通过对象池来管理短生命周期的对象。

8. **避免性能瓶颈**:
    - 通过减少不必要的对象创建和销毁，可以避免成为性能瓶颈。
    - 例如，避免在循环中频繁创建大对象。

### 实际应用场景

在实际应用中，频繁 Minor GC 的问题通常出现在以下场景：
- 当应用程序频繁创建大量临时对象时。
- 当内存分配策略不适合应用程序的工作负载时。

### 总结

这段代码示例通过展示如何创建一个可能导致频繁 Minor GC 的场景，体现了内存管理、对象生命周期、性能监控、异常定位、代码简洁性、可扩展性、资源管理和避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。

通过观察 GC 的行为，可以理解如何通过调整 JVM 参数来优化内存使用，减少 GC 的频率，从而提高应用程序的整体性能。