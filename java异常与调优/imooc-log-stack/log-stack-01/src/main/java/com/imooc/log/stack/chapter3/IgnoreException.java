package com.imooc.log.stack.chapter3;

import lombok.Data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * <h1>忽略异常</h1>
 * */
public class IgnoreException {

    @Data
    public static class Imoocer {
        private String name;
        private String gender;
    }

    // 1. for 循环中大批量的处理数据, 一般都不会让异常直接抛出
    public void batchProcess(List<Imoocer> imoocers) {

        int num = 0;
        for (Imoocer imoocer : imoocers) {
            try {
                num += (imoocer.getGender().equals("m") ? 0 : 1);
            } catch (Exception ex) {
                // 记录下异常情况
            }
        }

        System.out.println("female imoocer num is: " + num);
    }

    // 2. 存在网络请求(RPC), 允许一定次数的失败重试, 即忽略掉偶发性的异常
    private static void sendGet() {
        String urlString = "http://www.imooc.com"; // 注意URL需要包含协议头
        int retryCount = 0;
        int MAX_RETRIES = 3;
        int RETRY_DELAY_MS = 3;
        HttpURLConnection con = null;

        while (retryCount <= MAX_RETRIES) {
            try {
                // 创建URL对象
                URL obj = new URL(urlString);

                // 打开连接
                con = (HttpURLConnection) obj.openConnection();

                // 设置请求方法
                con.setRequestMethod("GET");

                // 获取响应码
                int responseCode = con.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                // 如果请求成功，直接返回
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return;
                }

                // 如果请求失败但未达到最大重试次数，则等待一段时间后重试
                if (retryCount < MAX_RETRIES) {
                    Thread.sleep(RETRY_DELAY_MS);
                }
            } catch (IOException e) {
                System.out.println("IOException occurred: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted: " + e.getMessage());
            } finally {
                if (con != null) {
                    con.disconnect(); // 关闭连接
                }
            }

            retryCount++; // 增加重试计数
        }

        // 如果所有重试都失败了，则输出一条消息
        System.out.println("Failed to connect after " + MAX_RETRIES + " attempts.");
    }

    // 3. 不影响业务的整体逻辑情况, 例如手机验证码发送失败
}
