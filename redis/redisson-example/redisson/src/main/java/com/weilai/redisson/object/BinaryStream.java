package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class BinaryStream {

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() throws IOException {
        log.info("BinaryStream init");
        redisson.getKeys().flushall();
        // 获取一个二进制流对象
        RBinaryStream stream = redisson.getBinaryStream("myStream");

        // 定义要写入的字节数组
        byte[] values = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        // 尝试设置字节数组
        log.info("stream.setIfAbsent(values): {}", stream.setIfAbsent(values));

        // 设置字节数组
        stream.set(values);

        // 从二进制流中读取数据
        log.info("Reading from input stream");
        InputStream is = stream.getInputStream();

        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }

        log.info("Read data: {}", sb.toString().getBytes());

        // 向二进制流中写入数据
        log.info("Writing to output stream");
        OutputStream os = stream.getOutputStream();
        for (int i = 0; i < values.length; i++) {
            byte c = values[i];
            os.write(c);
        }
        log.info("Data written to output stream");
    }
}