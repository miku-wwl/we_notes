package com.example.protobufdemo;

import com.example.protobufdemo.protobuf.User;
import com.example.protobufdemo.protobuf.UserList;
import com.example.protobufdemo.service.ProtobufService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProtobufdemoApplicationTests {

    @Autowired
    private ProtobufService protobufService;

    @Test
    void testUserCreation() {
        User user = protobufService.createSampleUser();

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertTrue(user.hasAddress()); // 验证地址是否存在
    }

    @Test
    void testSerializationAndDeserialization() throws Exception {
        // 创建原始用户
        User originalUser = protobufService.createSampleUser();

        // 序列化
        byte[] serializedData = protobufService.serializeUser(originalUser);
        assertNotNull(serializedData);
        assertTrue(serializedData.length > 0);

        // 反序列化
        User deserializedUser = protobufService.deserializeUser(serializedData);

        // 验证数据一致性
        assertEquals(originalUser, deserializedUser); // Protobuf 生成的类重写了 equals 方法
        assertEquals(originalUser.getAddress(), deserializedUser.getAddress());
    }

    @Test
    void testUserList() {
        UserList userList = protobufService.createUserList();

        assertNotNull(userList);
        assertEquals(2, userList.getUsersCount());
        assertEquals("John Doe", userList.getUsers(0).getName());
        assertEquals("Jane Smith", userList.getUsers(1).getName());
    }

}
