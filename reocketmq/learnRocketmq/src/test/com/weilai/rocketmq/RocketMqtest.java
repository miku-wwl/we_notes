package com.weilai.rocketmq;

import com.weilai.rocketmq.mq.Order;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest(classes = RocketmqApplication.class)
public class RocketMqtest {

    @Value("${topic.data-center.example}")
    private String topic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSend() {
        GenericMessage message = new GenericMessage("imooc test send msg");
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送消息成功... " + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送消息失败: " + throwable);
            }
        });
        try {
            Thread.sleep(50000);
        } catch (Exception e) {

        }
    }

    @Test
    public void testSendRepeat() {
        GenericMessage message = new GenericMessage("imooc test send repeat msg");
        rocketMQTemplate.asyncSend("repeat-topic", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送消息成功... " + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送消息失败: " + throwable);
            }
        });
        try {
            Thread.sleep(50000);
        } catch (Exception e) {

        }
    }

    @Test
    public void testSendRepeat2() {
        Order order = new Order();
        order.setOrderName("苹果");
        order.setOrderId(3);
        rocketMQTemplate.asyncSend("repeat-topic2", order, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送消息成功... " + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送消息失败: " + throwable);
            }
        });
        try {
            Thread.sleep(50000);
        } catch (Exception e) {

        }
    }

    @Test
    public void testSendTransaction() {
        GenericMessage message = new GenericMessage("imooc test send transaction msg");
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("transaction-topic", message, null);
        System.out.println("transactionSendResult: " + transactionSendResult);
        try {
            Thread.sleep(50000);
        } catch (Exception e) {

        }
    }
}
