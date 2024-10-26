package com.weilai.provider.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class UserDubboImpl implements UserDubbo {
    @Override
    public String getUserId(int userId) {
        log.info("userId={}", userId);
        return "user id" + userId;
    }
}