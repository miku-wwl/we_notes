package com.weilai.postgres.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderVo {
    private long orderId;

    private int orderType;

    private int userId;

    private long addressId;

    private String status;
}
