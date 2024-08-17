package com.imooc.log.stack.chapter2.java_process_exception;

import java.io.EOFException;
import java.io.FileNotFoundException;

/**
 * <h1>方式一: 声明异常</h1>
 * throw, throws
 * */
public class DeclareException {

    /**
     * <h2>使用 throw 关键字抛出运行时异常</h2>
     * */
    private static boolean validate01(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        return "qinyi".equals(name);
    }

    /**
     * <h2>编译期异常, 必须处理这个异常, 或者是由 throws 继续抛出给上层调用者处理</h2>
     * */
    private static void validate02(String name) throws EOFException,
            FileNotFoundException {

        if (null == name) {
            throw new EOFException("name is null...");
        }

        if (!"qinyi".equals(name)) {
            throw new FileNotFoundException("name is not qinyi...");
        }
    }
}
