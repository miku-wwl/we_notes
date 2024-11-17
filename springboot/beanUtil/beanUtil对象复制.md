``` java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long orderId;

    private int orderType;

    private int userId;

    private long addressId;

    private String status;
}

package com.weilai.zk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    private long orderId;

    private String orderType;

    private int userId;

    private int other;
}

package com.weilai.zk;

import org.springframework.beans.BeanUtils;

public class OrderMain {
    public static void main(String[] args) {
        Order order = new Order(1, 2, 3, 4, "5");
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        System.out.println(orderVo);
    }
}
```