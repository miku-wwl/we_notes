package com.weilai.rocketmq.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "${topic.data-center.example}", consumerGroup = "mygroup")
public class RocketMqConsumer implements RocketMQListener {
    @Override
    public void onMessage(Object o) {
        System.out.println("rocketMq 接收到 消息: " + o);
    }
}
