package com.weilai.rocketmq.mq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RocketMQMessageListener(topic = "repeat-topic", consumerGroup = "mygroup2")
public class RepeatRocketMqConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        String msgId = messageExt.getMsgId();
        if (Redis.tryLock(msgId, 120)) {
            System.out.println("rocketMq 接收到 第一次的 消息: " + new String(messageExt.getBody()));
            //制造这个错误，只是为了去还原 rocketMq重复发送消息
            int a = 1 / 0;
        } else {
            System.out.println("重复消息，丢弃。。。");
        }
    }

    public static class Redis {
        private static Map<String, Object> map = new HashMap<>();

        /**
         * 简单模拟redis
         *
         * @param key
         * @param seconds
         * @return
         */
        public static boolean tryLock(String key, int seconds) {
            if (map.containsKey(key)) {
                return false;
            } else {
                map.put(key, key);
                return true;
            }
        }
    }
}

