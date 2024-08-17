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
