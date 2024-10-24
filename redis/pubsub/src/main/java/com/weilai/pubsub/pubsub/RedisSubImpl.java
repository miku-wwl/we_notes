package com.weilai.pubsub.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weilai.pubsub.msg.CommonMsg;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisSubImpl {
    @Autowired
    private RedisConnectionFactory connectionFactory;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

    @PostConstruct
    public void init() {
        container.setConnectionFactory(connectionFactory);
        container.afterPropertiesSet();
        container.addMessageListener(
                (message, pattern) -> {
                    CommonMsg msg = null;
                    try {
                        msg = objectMapper.readValue(new String(message.getBody()), CommonMsg.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } finally {
                        log.info("receive msg:{}", msg);
                    }
                },
                ChannelTopic.of("my-channel"));
        container.start();
    }
}
