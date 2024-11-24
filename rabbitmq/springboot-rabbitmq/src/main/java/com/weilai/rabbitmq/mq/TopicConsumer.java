package com.weilai.rabbitmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            key = {"liu.shui.jing"},
            exchange = @Exchange(name = "topic", type = "topic")
    ))
    public void receiveOne(String message) {
        log.info("receiveOne message：{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            key = {"liu.shui.*"},
            exchange = @Exchange(name = "topic", type = "topic")
    ))
    public void receiveTwo(String message) {
        log.info("receiveTwo message：{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            key = {"liu.shui.#"},
            exchange = @Exchange(name = "topic", type = "topic")
    ))
    public void receiveThree(String message) {
        log.info("receiveThree message：{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            key = {"liu.#"},
            exchange = @Exchange(name = "topic", type = "topic")
    ))
    public void receiveFour(String message) {
        log.info("receiveFour message：{}", message);
    }

}
