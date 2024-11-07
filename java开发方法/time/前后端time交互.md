``` java
package com.weilai.sqlite.controller;

import com.weilai.sqlite.entity.User;
import com.weilai.sqlite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * 前端使用JavaScript 解析LocalDateTime
     * // 给定的日期时间字符串
     * const input = "2024-11-01T10:17:12.92";
     *
     * // 使用 Date 对象解析输入的字符串
     * const date = new Date(input);
     *
     * // 获取年、月、日、小时、分钟和秒
     * const year = date.getFullYear();
     * const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要加1
     * const day = String(date.getDate()).padStart(2, '0');
     * const hours = String(date.getHours()).padStart(2, '0');
     * const minutes = String(date.getMinutes()).padStart(2, '0');
     * const seconds = String(date.getSeconds()).padStart(2, '0');
     *
     * // 拼接成所需的格式
     * const formattedDate = `${year}.${month}.${day}.${hours}:${minutes}:${seconds}`;
     *
     * // 输出结果
     * console.log(formattedDate); // 应该打印出 2024.11.01.10:17:12
     *
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/save-user")
    public User saveUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
```