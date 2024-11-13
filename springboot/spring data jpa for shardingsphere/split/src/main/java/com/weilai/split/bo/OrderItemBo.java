package com.weilai.split.bo;

import lombok.Data;

@Data
public class OrderItemBo {
    private long orderItemId;

    private long orderId;

    private int userId;

    private String phone;

    private String status;
}
