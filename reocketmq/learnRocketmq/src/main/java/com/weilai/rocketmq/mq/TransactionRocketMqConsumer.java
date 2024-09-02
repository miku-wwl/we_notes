package com.weilai.rocketmq.mq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "transaction-topic", consumerGroup = "mygroup3")
public class TransactionRocketMqConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("消费事务消息: " + new String(messageExt.getBody()));
    }
}
