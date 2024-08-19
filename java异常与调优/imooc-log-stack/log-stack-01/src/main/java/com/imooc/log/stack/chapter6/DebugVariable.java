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


这段代码展示了如何使用调试工具来跟踪、计算和修改变量的值。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义 Imoocer 类**:
   - `Imoocer` 类是一个简单的 POJO 类，包含三个字段：`name`, `age`, 和 `salary`。
   - 通过 Lombok 的 `@Data`, `@NoArgsConstructor`, 和 `@AllArgsConstructor` 注解自动生成了 getter, setter, 构造器等方法。

2. **判断是否为机器人 Imoocer**:
   - `isRobotImoocer` 方法接收一个 `Imoocer` 对象，并检查其名字是否以 "robot" 结尾。
   - 如果名字以 "robot" 结尾，则返回 `true`，否则返回 `false`。

3. **对象转 Map**:
   - `objToMap` 方法接收一个 `Imoocer` 对象，并将其转换为 `Map<String, String>` 类型。
   - 在转换之前，先检查 `Imoocer` 是否是机器人，如果是，则抛出一个运行时异常。
   - 如果 `Imoocer` 的名字为空，则将其设置为默认值 "imooc-qinyi"。
   - 最后将 `Imoocer` 的属性转换为字符串，并添加到 `Map` 中。

4. **主函数**:
   - `main` 方法创建了一个 `Imoocer` 对象，并调用 `objToMap` 方法来转换该对象为 `Map`。

### 调优思想

1. **异常处理**:
   - 在 `objToMap` 方法中，通过检查 `Imoocer` 是否是机器人，并在符合条件时抛出异常，可以有效地处理不期望的情况。
   - 这有助于确保代码的健壮性，并防止意外的逻辑分支。

2. **防御性编程**:
   - 在转换 `Imoocer` 对象为 `Map` 之前，检查其名字是否为空，并在必要时设置默认值。
   - 这种做法可以确保方法的输出总是有效的，即使输入数据不完整。

3. **可读性和可维护性**:
   - 通过将逻辑分解为多个方法（例如 `isRobotImoocer`），可以提高代码的可读性和可维护性。
   - 这有助于其他开发者更容易地理解代码的意图。

4. **使用工具类简化逻辑**:
   - 使用 Hutool 的 `StrUtil.isEmpty` 方法来检查字符串是否为空，这是一种简洁的方法来处理空字符串的情况。
   - 这种做法可以避免编写冗余的空检查逻辑。

5. **异常处理的调试**:
   - 在 `objToMap` 方法中，通过抛出异常来处理不期望的情况，这有助于在调试时识别问题。
   - 通过调试工具跟踪变量的值，可以更好地理解异常是如何触发的。

### 实际应用场景

在实际应用中，这种使用调试工具跟踪变量的方法适用于以下场景：
- 当需要检查特定条件下的异常行为时。
- 当需要确保代码的健壮性，防止不期望的数据进入关键逻辑时。
- 当需要提高代码的可读性和可维护性时。

总结来说，这段代码示例通过展示如何使用调试工具来跟踪、计算和修改变量的值，体现了异常处理、防御性编程、可读性和可维护性、使用工具类简化逻辑以及异常处理的调试等调优思想。这对于提高代码的质量和健壮性非常重要。