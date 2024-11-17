package com.weilai.sharding.bo;

import lombok.Data;

@Data
public class OrderBo {
    private long orderId;

    private int orderType;

    private int userId;

    private long addressId;

    private String status;
}