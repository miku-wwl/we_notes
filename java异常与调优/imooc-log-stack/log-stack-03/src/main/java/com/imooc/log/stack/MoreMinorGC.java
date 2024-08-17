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
