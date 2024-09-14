package com.weilai.rlock;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RlockApplicationTests {
    private String order = "";

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test() {
        // 创建并启动多个线程来处理订单
        Thread thread1 = new Thread(() -> processOrder("1"));
        Thread thread2 = new Thread(() -> processOrder("2"));
        Thread thread3 = new Thread(() -> processOrder("3"));
        Thread thread4 = new Thread(() -> processOrder("4"));
        Thread thread5 = new Thread(() -> processOrder("5"));
        Thread thread6 = new Thread(() -> processOrder("6"));
        Thread thread7 = new Thread(() -> processOrder("7"));
        Thread thread8 = new Thread(() -> processOrder("8"));
        Thread thread9 = new Thread(() -> processOrder("9"));
        Thread thread10 = new Thread(() -> processOrder("10"));
        Thread thread11 = new Thread(() -> processOrder("11"));
        Thread thread12 = new Thread(() -> processOrder("12"));

        // 启动所有线程
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread11.start();
        thread12.start();

        // 等待所有线程完成
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
            thread9.join();
            thread10.join();
            thread11.join();
            thread12.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for threads to complete", e);
        }
    }

    public void processOrder(String orderId) {
        // 获取订单ID对应的分布式锁
        RLock orderLock = redissonClient.getLock("order:" + orderId);

        try {
            // 尝试获取锁，等待时间30秒，锁自动释放时间60秒
            // 注意：这里设置的锁的自动释放时间实际上是由Watchdog机制管理的，Redisson的RLock已经内置了Watchdog功能
            boolean isLocked = orderLock.tryLock(30, 60, TimeUnit.SECONDS);

            if (!isLocked) {
                // 未能获取锁，表明已经有另一个线程正在处理此订单
                System.out.println("Another thread is processing the order " + orderId);
                return;
            }

            // 执行订单处理逻辑
            handleOrder(orderId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while trying to lock", e);
        } finally {
            // 释放锁
            // 使用isHeldByCurrentThread 解决被其他的线程释放锁的问题。
            if (orderLock.isHeldByCurrentThread()) {
                orderLock.unlock();
            }
        }
    }

    /**
     * 具体的订单处理逻辑
     *
     * @param orderId 订单ID
     */
    private void handleOrder(String orderId) {
        // 模拟订单处理逻辑
        order = orderId;
        System.out.println("Handling order: " + order);
        // 调用仓储服务API或其他逻辑...
    }

}

