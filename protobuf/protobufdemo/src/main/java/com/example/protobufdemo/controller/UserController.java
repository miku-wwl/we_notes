package com.example.protobufdemo.controller;

import com.example.protobufdemo.protobuf.User;
import com.example.protobufdemo.protobuf.UserList;
import com.example.protobufdemo.service.ProtobufService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final ProtobufService protobufService;

    // 构造函数注入（Spring Boot 3 推荐）
    public UserController(ProtobufService protobufService) {
        this.protobufService = protobufService;
    }

    @GetMapping("/user")
    public String getUser() {
        User user = protobufService.createSampleUser();
        return protobufService.formatUserInfo(user);
    }

    // 返回 Protobuf 二进制数据
    @GetMapping(value = "/user/protobuf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getUserAsProtobuf() {
        User user = protobufService.createSampleUser();
        return protobufService.serializeUser(user);
    }

    // 返回用户列表
    @GetMapping("/user/list")
    public String getUserList() {
        UserList userList = protobufService.createUserList();
        StringBuilder sb = new StringBuilder("User List:\n");

        // 使用 JDK 17 的增强 for 循环
        for (User user : userList.getUsersList()) {
            sb.append("---\n").append(protobufService.formatUserInfo(user)).append("\n");
        }

        return sb.toString();
    }
}