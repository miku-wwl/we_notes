package com.weilai.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weilai.pubsub.msg.CommonMsg;
import com.weilai.pubsub.pubsub.Publish;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PubsubApplication {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        ConfigurableApplicationContext context = SpringApplication.run(PubsubApplication.class, args);
        Publish publish = context.getBean(Publish.class);
        for (int i = 0; i < 10; i++) {
            CommonMsg commonMsg = new CommonMsg(i);
            publish.pub("my-channel", objectMapper.writeValueAsString(commonMsg));
        }
    }
}
