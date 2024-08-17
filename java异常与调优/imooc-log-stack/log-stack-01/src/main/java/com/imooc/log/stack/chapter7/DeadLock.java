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
