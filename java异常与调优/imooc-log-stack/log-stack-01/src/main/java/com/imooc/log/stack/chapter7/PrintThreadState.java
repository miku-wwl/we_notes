package com.imooc.log.stack.chapter7;

import java.util.concurrent.locks.LockSupport;

/**
 * <h1>打印线程状态</h1>
 * */
public class PrintThreadState {

    /**
     * <h2>New</h2>
     * */
    public static void newState() {

        System.out.println(new Thread().getState());
    }

    /**
     * <h2>RUNNABLE</h2>
     * (1) READY
     * (2) RUNNING
     * */
    public static void runnableState() throws Exception {

        Thread thread = new Thread(()-> {
            while (true){
            }
        });

        thread.start();
        System.out.println(thread.getState());
    }

    /**
     * <h2>BLOCKED</h2>
     * */
    public static void blockedState() throws Exception {

        Object MONITOR = new Object();

        Thread thread1 = new Thread(()-> {
            synchronized (MONITOR) {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                }
            }
        });

        Thread thread2 = new Thread(()-> {
            synchronized (MONITOR) {}
        });

        thread1.start();
        Thread.sleep(100);

        thread2.start();
        Thread.sleep(100);

        System.out.println(thread2.getState());
    }

    /**
     * <h2>WAITING</h2>
     * */
    public static void waitingState() throws Exception {

        Thread thread = new Thread(()-> {
            LockSupport.park();
            while (true) {
            }
        });

        thread.start();
        Thread.sleep(100);
        System.out.println(thread.getState());  // 这里输出 WAITING

        LockSupport.unpark(thread);
        Thread.sleep(100);
        System.out.println(thread.getState());  // 这里输出 RUNNABLE
    }

    /**
     * <h2>TIMED_WAITING</h2>
     * */
    public static void timedwaitingState() throws Exception {

        Thread thread = new Thread(()-> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        });

        thread.start();
        Thread.sleep(100);
        System.out.println(thread.getState());
    }

    /**
     * <h2>TERMINATED</h2>
     * */
    public static void terminatedState() throws Exception {

        Thread thread = new Thread(() -> {});
        thread.start();

        Thread.sleep(100);
        System.out.println(thread.getState());
    }

    public static void main(String[] args) throws Exception {

//        newState();
//        runnableState();
//        blockedState();
        waitingState();
//        timedwaitingState();
//        terminatedState();
    }
}


这段代码示例展示了如何在 Java 中创建不同的线程并观察它们的状态。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **New 状态**:
   - `newState` 方法创建了一个新的线程对象，但并没有启动它。
   - 因此，该线程处于 `NEW` 状态。

2. **Runnable 状态**:
   - `runnableState` 方法创建了一个无限循环的线程。
   - 启动线程后，它将处于 `RUNNABLE` 状态。
   - 这个状态包括 `READY` 和 `RUNNING` 子状态。

3. **Blocked 状态**:
   - `blockedState` 方法创建了两个线程，其中一个线程获取了锁，而另一个线程试图获取同一个锁。
   - 第二个线程将处于 `BLOCKED` 状态，因为它在等待锁。

4. **Waiting 状态**:
   - `waitingState` 方法创建了一个线程，该线程使用 `LockSupport.park()` 方法挂起。
   - 当线程被挂起时，它处于 `WAITING` 状态。
   - 使用 `LockSupport.unpark()` 方法可以解除挂起状态，使线程回到 `RUNNABLE` 状态。

5. **Timed_Waiting 状态**:
   - `timedwaitingState` 方法创建了一个线程，该线程调用 `Thread.sleep(5000)` 来休眠 5 秒钟。
   - 在这段时间内，线程处于 `TIMED_WAITING` 状态。

6. **Terminated 状态**:
   - `terminatedState` 方法创建了一个线程，该线程没有任何操作，立即结束。
   - 一旦线程完成执行，它将处于 `TERMINATED` 状态。

### 调优思想

1. **性能监控**:
   - 通过观察线程状态，可以监控应用程序的性能。
   - 例如，如果发现有大量的线程处于 `TIMED_WAITING` 或 `WAITING` 状态，可能意味着应用程序中有过多的等待或阻塞操作，这可能是性能瓶颈。

2. **异常定位**:
   - 通过线程状态可以辅助定位异常发生的位置。
   - 例如，如果一个线程长时间处于 `BLOCKED` 状态，可能是因为它无法获得所需的锁，这可能导致死锁或其他并发问题。

3. **线程状态分析**:
   - 了解线程状态可以帮助分析线程的行为。
   - 例如，如果发现线程频繁地在 `RUNNABLE` 和 `TIMED_WAITING` 之间切换，可能意味着存在 I/O 阻塞或等待事件。

4. **调试和故障排除**:
   - 线程状态信息对于调试多线程应用程序中的问题非常有用。
   - 通过观察线程的状态，可以快速定位问题，并理解线程间的交互。

5. **性能优化**:
   - 通过减少不必要的 `TIMED_WAITING` 和 `WAITING` 状态，可以提高应用程序的性能。
   - 例如，通过优化锁的使用方式或减少不必要的同步操作，可以减少线程的等待时间。

6. **代码简洁性**:
   - 代码中使用了简洁的方法来创建和启动线程，这有助于提高代码的可读性。
   - 使用 `Thread.sleep` 和 `LockSupport` 类提供了简单的方法来模拟线程的不同状态。

7. **代码可读性**:
   - 通过为每种线程状态创建单独的方法，可以提高代码的组织性和可读性。
   - 这使得其他开发者更容易理解每个方法的目的和作用。

### 实际应用场景

在实际应用中，观察线程状态的方法适用于以下场景：
- 当需要监控应用程序的性能和状态时。
- 当需要调试多线程应用程序中的问题时。
- 当需要优化应用程序的性能时。

总结来说，这段代码示例通过展示如何创建不同状态的线程并观察它们的状态，体现了性能监控、异常定位、线程状态分析、调试和故障排除、性能优化、代码简洁性以及代码可读性等调优思想。这对于提高应用程序的性能和稳定性非常重要。