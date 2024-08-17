package com.imooc.log.stack;

import java.util.Properties;

/**
 * <h1>Java 系统属性</h1>
 * */
public class JavaSystemProperties {

    public static void main(String[] args) {

        // 打印所有的 Java 系统属性
        Properties pros = System.getProperties();
        pros.list(System.out);

        System.out.println("//////////////////////////////////////////////////////////////////");

        // 获取特定的 Java 系统属性, key 不存在则返回 null
        System.out.println(System.getProperty("java.home"));        // JRE 主目录
        System.out.println(System.getProperty("java.library.path"));        // 用于搜索本机库的 JRE 库搜索路径
        System.out.println(System.getProperty("java.ext.dirs"));        // JRE扩展库路径
        System.out.println(System.getProperty("java.class.path"));      // JRE类路径
        System.out.println(System.getProperty("java.version"));     // Java 版本
        System.out.println(System.getProperty("imooc-qinyi"));
    }
}
