package com.weilai.dubboprovider.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@Slf4j
public class UserDubboImpl implements UserDubbo {
    @Override
    public String getUserId(int userId) {
        log.info("userId={}", userId);
        return "user id" + userId;
    }
}
