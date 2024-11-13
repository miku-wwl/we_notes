package com.weilai.sharding.controller;


import com.weilai.sharding.bo.OrderBo;
import com.weilai.sharding.entity.Order;
import com.weilai.sharding.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }
}
