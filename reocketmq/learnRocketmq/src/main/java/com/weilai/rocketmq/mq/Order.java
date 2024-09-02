package com.weilai.rocketmq.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private Integer orderId;
    private String orderName;
}

