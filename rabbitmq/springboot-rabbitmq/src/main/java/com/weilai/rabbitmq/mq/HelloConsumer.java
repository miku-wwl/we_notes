package com.weilai.rabbitmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class HelloConsumer {

    @RabbitHandler
    public void receive(String message) {
        log.info(message);
    }
}
