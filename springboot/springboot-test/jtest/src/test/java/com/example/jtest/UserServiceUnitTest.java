package com.example.jtest;

import com.example.jtest.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 不添加任何Spring相关注解，纯JUnit测试
public class UserServiceUnitTest {

    // 直接new对象（不依赖Spring注入）
    private final UserService userService = new UserService();

    @Test
    public void testCalculateAge_ValidBirthday() {
        // 场景1：正常生日（2000-01-01），计算2025年的年龄
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        int age = userService.calculateAge(birthday);
        assertEquals(25, age); // 断言结果正确
    }

    @Test
    public void testCalculateAge_NullBirthday() {
        // 场景2：生日为null，返回0
        int age = userService.calculateAge(null);
        assertEquals(0, age);
    }

    @Test
    public void testCalculateAge_FutureBirthday() {
        // 场景3：未来生日（2030-01-01），返回0
        LocalDate futureBirthday = LocalDate.of(2030, 1, 1);
        int age = userService.calculateAge(futureBirthday);
        System.out.println("age=" + age);
        assertEquals(-4, age);
    }
}