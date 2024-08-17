package com.imooc.log.stack.chapter2.java_process_exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>方式2: 捕获异常</h1>
 * try...catch...finally
 * */
@SuppressWarnings("all")
public class CatchException {

    /**
     * <h2>validate01 抛出单个异常</h2>
     * */
    private static boolean validate01(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        return "qinyi".equals(name);
    }

    /**
     * <h2>validate02 抛出多个异常</h2>
     * */
    private static boolean validate02(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        if ("".equals(name)) {
            throw new IllegalArgumentException("name is blank...");
        }

        if (!"qinyi".equals(name)) {
            throw new RuntimeException("name is not qinyi...");
        }

        return true;
    }

    /**
     * <h2>打开并关闭 Stream</h2>
     * */
    private static void openAndCloseStream() {

        Stream<Path> pathStream = null;

        try {
            pathStream = Files.list(Paths.get("/tmp"));
            List<Path> paths = pathStream.collect(Collectors.toList());
            System.out.println(paths.size());
            // ....
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != pathStream) {
                pathStream.close();
            }
        }
    }

    public static void main(String[] args) {
//
//        // 1. 捕获单个异常
//        try {
//            validate01(null);
//        } catch (Throwable th) {
//            System.out.println(th.getMessage());
//            th.printStackTrace();
//        }
//
//        // 2.1 捕获多个异常 -- 第一种方法, 多一个异常一次捕获多次处理
//        try {
//            validate02("");
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } catch (IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
//
//        // 2.2 捕获多个异常 -- 第二种方式, 一个 try, 一个 catch
//        try {
//            validate02("");
//        } catch (NullPointerException | IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
//
//        // 2.3 捕获多个异常 -- 第三种方式, 定义一个范围更大的父类异常对象
//        try {
//            validate02("");
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }

        openAndCloseStream();
    }
}
