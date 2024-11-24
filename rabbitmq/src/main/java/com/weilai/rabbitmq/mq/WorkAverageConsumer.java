package com.weilai.rabbitmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkConsumer {

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiveOne(String message) {
        log.info("{} 被 receiveOne 消费", message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiveTwo(String message) {
        log.info("{} 被 receiveTwo 消费", message);
    }

}
