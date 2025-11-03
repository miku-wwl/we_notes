package com.example.jtest;

import com.example.jtest.entity.User;
import com.example.jtest.repository.UserRepository;
import com.example.jtest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // 启动Spring Boot应用上下文
@ActiveProfiles("test") // 指定使用test环境配置（application-test.properties）
public class UserServiceIntegrationTest {

    // 依赖Spring注入的Service和Repository
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // 1. 准备测试数据
        User user = new User("张三", LocalDate.of(2000, 1, 1));

        // 2. 调用Service方法（保存用户到H2数据库）
        User savedUser = userService.saveUser(user);

        // 3. 验证结果
        assertNotNull(savedUser.getId()); // 主键应自动生成
        assertEquals("张三", savedUser.getName());

        // 4. 从数据库查询验证
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(LocalDate.of(2000, 1, 1), foundUser.getBirthday());
    }
}