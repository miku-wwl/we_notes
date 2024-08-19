package com.imooc.log.stack.chapter7;

/**
 * <h1>通过线程堆栈日志定位并解决死锁问题</h1>
 * */
public class DeadLock {

    private static final Object obj1 = new Object();
    private static final Object obj2 = new Object();

    /**
     * <h2>死锁案例</h2>
     * */
    private static void deadLockExample() {

        final Object o1 = new Object();
        final Object o2 = new Object();

        Runnable r1 = () -> {

            synchronized (o1) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("R1 Done!");
                }
            }
        };

        Runnable r2 = () -> {

            synchronized (o2) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("R2 Done!");
                }
            }
        };

        new Thread(r1, "Thread1").start();
        new Thread(r2, "Thread2").start();
    }

    /**
     * <h2>以固定的顺序去获取锁</h2>
     * */
    private static void fixedOrderGetLock() {

        synchronized (obj1) {
            System.out.println(Thread.currentThread().getName() + " get lock obj1 success!");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            synchronized (obj2) {
                System.out.println(Thread.currentThread().getName() + " get lock obj2 cuccess!");
            }
        }
    }

    private static void hasNotDeadLockExample() {

        Runnable r1 = DeadLock::fixedOrderGetLock;
        Runnable r2 = DeadLock::fixedOrderGetLock;

        new Thread(r1, "Thread1").start();
        new Thread(r2, "Thread2").start();
    }

    public static void main(String[] args) {

//        deadLockExample();

        hasNotDeadLockExample();
    }
}



这段代码示例展示了如何通过线程堆栈日志来定位并解决 Java 中的死锁问题。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **死锁案例**:
   - `deadLockExample` 方法创建了两个线程 `r1` 和 `r2`，每个线程都试图获取两个不同的锁 `o1` 和 `o2`。
   - 线程 `r1` 首先获取 `o1` 锁，然后尝试获取 `o2` 锁。
   - 线程 `r2` 首先获取 `o2` 锁，然后尝试获取 `o1` 锁。
   - 因为两个线程都持有其中一个锁，并尝试获取另一个线程持有的锁，所以会导致死锁。

2. **以固定的顺序获取锁**:
   - `fixedOrderGetLock` 方法展示了如何通过确保线程以相同的顺序获取锁来避免死锁。
   - 在这个方法中，所有线程首先获取 `obj1` 锁，然后再获取 `obj2` 锁。
   - 通过这种方式，可以避免死锁的发生。

3. **避免死锁的示例**:
   - `hasNotDeadLockExample` 方法创建了两个线程，这两个线程都调用 `fixedOrderGetLock` 方法。
   - 由于线程以相同的顺序获取锁，因此不会发生死锁。

### 调优思想

1. **避免死锁**:
   - 确保线程以相同的顺序获取锁可以有效避免死锁。
   - 通过固定锁的获取顺序，可以确保不会出现两个线程相互等待对方释放锁的情况。

2. **线程堆栈日志**:
   - 使用线程堆栈日志可以帮助诊断死锁问题。
   - 当发现死锁时，可以使用线程堆栈日志来确定哪些线程处于等待状态，以及它们正在等待哪些锁。

3. **异常处理**:
   - 在示例中，使用 `try-catch` 块来处理 `InterruptedException`，这是因为 `Thread.sleep` 方法会抛出此异常。
   - 正确处理异常可以避免程序崩溃或产生未预期的行为。

4. **代码可读性**:
   - 通过将锁获取逻辑封装在单独的方法中（如 `fixedOrderGetLock`），可以提高代码的可读性和可维护性。
   - 这种做法使得其他开发者更容易理解锁的获取顺序。

5. **性能优化**:
   - 通过避免死锁可以提高程序的性能，因为死锁会导致资源浪费和响应时间延长。
   - 确保线程以相同的顺序获取锁可以减少不必要的等待时间。

### 实际应用场景

在实际应用中，避免死锁是非常重要的，特别是在多线程环境中。以下是一些应用场景：

1. **多线程应用程序**:
   - 当多个线程需要访问共享资源时，确保以相同的顺序获取锁可以避免死锁。

2. **数据库事务**:
   - 在数据库事务中，多个事务可能需要锁定同一组资源。确保以相同的顺序获取锁可以避免死锁。

3. **分布式系统**:
   - 在分布式系统中，多个节点可能需要访问共享资源。确保以相同的顺序获取锁可以避免跨节点的死锁。

### 总结

这段代码示例通过展示如何通过线程堆栈日志来定位并解决 Java 中的死锁问题，体现了避免死锁、使用线程堆栈日志、异常处理、代码可读性以及性能优化等调优思想。对于提高多线程程序的性能和稳定性非常重要。