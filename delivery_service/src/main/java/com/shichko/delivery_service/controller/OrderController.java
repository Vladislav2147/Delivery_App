package com.shichko.delivery_service.controller;

import com.shichko.delivery_service.model.entity.Order;
import com.shichko.delivery_service.model.repository.OrderRepository;
import com.shichko.delivery_service.model.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController extends CommonController<Order, OrderRepository, OrderService> {

    public OrderController(OrderService service) {
        super(service);
    }
}