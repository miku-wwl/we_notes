package com.imooc.log.stack;

/**
 * <h1>打印 GC 日志</h1>
 * -XX:NewSize=5M -XX:MaxNewSize=5M -XX:InitialHeapSize=10M -XX:MaxHeapSize=10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * */
@SuppressWarnings("all")
public class PrintGCLog {

    private static void gc01() {

        byte[] x = new byte[1024 * 1024]; // 1024 * 1024 分配 1MB 空间
        x = new byte[1024 * 1024];
        x = new byte[1024 * 1024];
        x = null;

        byte[] y = new byte[2 * 1024 * 1024]; // 2 * 1024 * 1024 分配 2MB 空间
    }

    public static void main(String[] args) {

        gc01();
    }
}


这段代码示例展示了如何在 Java 应用程序中触发 Minor GC（Young GC）并通过 JVM 参数打印详细的 GC 日志。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义 `gc01` 方法**:
   - 在方法内部，连续创建三个 1 MB 大小的字节数组对象 `x`。
   - 每次创建新数组时，都会将前一个数组赋值给 `null`，使其成为垃圾对象。
   - 最后创建一个 2 MB 大小的字节数组 `y`。
   - 由于 Eden 区的大小设置为 4 MB，并且 `y` 占用了 2 MB，这将导致 Eden 区填满。
   - 这将触发 Minor GC，以清理之前创建的垃圾对象。

2. **主函数 (`main` 方法)**:
   - 调用 `gc01` 方法，触发 Minor GC。

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

在实际应用中，打印 GC 日志的方法通常用于以下场景：
- 当需要监控应用程序的 GC 行为时。
- 当需要优化应用程序的内存使用时。
- 当需要理解 GC 的触发条件时。

### 总结

这段代码示例通过展示如何创建一个触发 Minor GC 的场景，并通过 JVM 参数打印详细的 GC 日志，体现了内存管理、对象生命周期、性能监控、异常定位、代码简洁性、可扩展性、资源管理和避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。

通过观察 GC 的行为，可以理解如何通过调整 JVM 参数来优化内存使用，减少 GC 的频率，从而提高应用程序的整体性能。