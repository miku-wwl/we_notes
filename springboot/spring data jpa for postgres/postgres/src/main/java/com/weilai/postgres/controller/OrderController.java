package com.weilai.postgres.controller;


import com.weilai.postgres.bo.OrderBo;
import com.weilai.postgres.entity.Order;
import com.weilai.postgres.repository.OrderRepository;
import com.weilai.postgres.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/save")
    public ResponseEntity<String> Save(@RequestBody OrderBo orderBo) {
        Order order = new Order(orderBo.getOrderId(), orderBo.getOrderType(), orderBo.getUserId(), orderBo.getAddressId(), orderBo.getStatus());
        orderRepository.save(order);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OrderVo>> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderVo> orderVo = orders.stream().map(it -> new OrderVo(it.getOrderId(), it.getOrderType(), it.getUserId(), it.getAddressId(), it.getStatus())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(orderVo);
    }
}
