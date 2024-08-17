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
