package com.imooc.log.stack.chapter6;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>通过 DEBUG 工具跟踪、计算、修改变量的值</h1>
 * */
public class DebugVariable {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Imoocer {

        private String name;
        private int age;
        private double salary;
    }

    private static boolean isRobotImoocer(Imoocer imoocer) {
        return imoocer.getName().endsWith("robot");
    }

    private static Map<String, String> objToMap(Imoocer imoocer) {

        System.out.println("coming in objToMap......");

        Map<String, String> result = new HashMap<>();

        boolean isRobot = isRobotImoocer(imoocer);

        if (isRobot) {
            throw new RuntimeException("imoocer is robot");
        }

        if (StrUtil.isEmpty(imoocer.getName())) {
            imoocer.setName("imooc-qinyi");
        }

        result.put("name", imoocer.getName());
        result.put("age", String.valueOf(imoocer.getAge()));
        result.put("salary", String.valueOf(imoocer.getSalary()));

        return result;
    }

    public static void main(String[] args) {

        Imoocer imoocer = new Imoocer("qinyi", 19, 0.0);
        System.out.println(objToMap(imoocer));
    }
}
