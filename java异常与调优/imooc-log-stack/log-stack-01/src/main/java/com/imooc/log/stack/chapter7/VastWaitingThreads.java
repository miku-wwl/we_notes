package com.imooc.log.stack.chapter7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

/**
 * <h1>通过线程堆栈定位大量 Waiting 状态的线程</h1>
 * */
@SuppressWarnings("all")
public class VastWaitingThreads {

    private static List<Thread> createAndParkThreads(int threadCount) {

        List<Thread> threads = new ArrayList<>(threadCount);

        for (int i = 0; i != threadCount; ++i) {
            Thread thread = new Thread(() -> {
                while (true) {
                    // 挂起线程
                    LockSupport.park();
                    System.out.println(Thread.currentThread() + " was park!");
                }
            });

            thread.setName("QinyiThread-" + i);
            threads.add(thread);
            thread.start();
        }

        return threads;
    }

    /**
     * <h2>随机的 unpark 某个线程</h2>
     * */
    private static void randomUnparkThread(List<Thread> threads) {

        while (true) {

            Thread t = threads.get(new Random().nextInt(threads.size()));
            if (null != t) {
                LockSupport.unpark(t);
                System.out.println(t.getName() + " unpark!");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        randomUnparkThread(createAndParkThreads(500));
    }
}
