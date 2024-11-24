package com.weilai.rabbitmq.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/")
    public void send(String routingKey, String message) {
        log.info("routingKey={}, message={}", routingKey, message);
        rabbitTemplate.convertAndSend(routingKey, message);
    }

    @PostMapping("/work")
    public void sendWork(String routingKey, String message) {
        for (int i = 1; i <= 10; i++) {
            rabbitTemplate.convertAndSend(routingKey, "第 " + i + " 条消息：" + message);
        }
    }

    @PostMapping("/fanout")
    public void sendFanout(String exchange, String message) {
        rabbitTemplate.convertAndSend(exchange, "", message);
    }

    @PostMapping("/direct")
    public void sendDirect(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
