package com.shichko.delivery_service.model.service;

import com.shichko.delivery_service.model.entity.Order;
import com.shichko.delivery_service.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends CrudService<Order, OrderRepository> {
    @Autowired
    public OrderService(OrderRepository repository) {
        super(repository);
    }
}