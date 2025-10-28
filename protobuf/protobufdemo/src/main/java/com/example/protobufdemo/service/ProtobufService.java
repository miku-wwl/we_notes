package com.example.protobufdemo.service;

import com.example.protobufdemo.protobuf.User;
import com.example.protobufdemo.protobuf.UserList;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ProtobufService {

    // 使用 JDK 17 的文本块和增强的 switch 表达式
    private static final String USER_INFO_FORMAT = """
            User Details:
            ID: %d
            Name: %s
            Email: %s
            Age: %d
            Active: %b
            %s""";

    // 创建示例用户
    public User createSampleUser() {
        // 构建地址对象
        User.Address address = User.Address.newBuilder()
                .setStreet("123 Main St")
                .setCity("Springfield")
                .setCountry("USA")
                .build();

        // 构建用户对象
        return User.newBuilder()
                .setId(1)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setAge(30)
                .setActive(true)
                .setAddress(address) // 设置可选地址
                .build();
    }

    // 序列化用户对象
    public byte[] serializeUser(User user) {
        return user.toByteArray();
    }

    // 反序列化用户对象
    public User deserializeUser(byte[] data) throws Exception {
        return User.parseFrom(data);
    }

    // 创建用户列表
    public UserList createUserList() {
        User user1 = createSampleUser();

        User user2 = User.newBuilder()
                .setId(2)
                .setName("Jane Smith")
                .setEmail("jane.smith@example.com")
                .setAge(28)
                .setActive(true)
                .build();

        return UserList.newBuilder()
                .addAllUsers(List.of(user1, user2)) // 使用 JDK 17 的 List.of
                .build();
    }

    // 格式化用户信息为字符串
    public String formatUserInfo(User user) {
        String addressInfo = user.hasAddress() ?
                "Address: " + user.getAddress().getStreet() + ", " +
                        user.getAddress().getCity() + ", " +
                        user.getAddress().getCountry() :
                "Address: Not provided";

        return USER_INFO_FORMAT.formatted( // 使用 JDK 15+ 的 formatted 方法
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getActive(),
                addressInfo
        );
    }
}