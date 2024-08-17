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
