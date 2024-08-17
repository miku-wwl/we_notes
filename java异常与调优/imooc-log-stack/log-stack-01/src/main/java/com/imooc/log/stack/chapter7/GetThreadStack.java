package com.imooc.log.stack.chapter7;

import java.util.Map;

/**
 * <h1>获取运行时线程堆栈</h1>
 * */
public class GetThreadStack {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();

        ts.keySet().forEach(thread -> {

            sb.append(thread.getName()).append(":").append(thread.getId()).append("\n");
            for (StackTraceElement ste : ts.get(thread)) {
                sb.append(ste).append("\n");
            }
            // 隔离开每一个线程
            sb.append("---------------------------------------------").append("\n");
        });

        System.out.println(sb.toString());
    }
}
