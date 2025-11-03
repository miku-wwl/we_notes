package com.example.jtest.service;

import com.example.jtest.entity.User;
import com.example.jtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class UserService {

    // 依赖Spring注入的Repository（集成测试用）
    @Autowired
    private UserRepository userRepository;

    // --------------- 纯逻辑方法（JUnit单元测试目标）---------------
    // 计算年龄：无依赖、纯逻辑，适合单独用JUnit测试
    public int calculateAge(LocalDate birthday) {
        if (birthday == null) return 0;
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthday, now);
    }

    // --------------- 依赖资源方法（SpringBoot集成测试目标）---------------
    // 保存用户：依赖Repository和数据库，需Spring容器支持
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // 根据ID查询用户：依赖数据库，需集成测试
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}