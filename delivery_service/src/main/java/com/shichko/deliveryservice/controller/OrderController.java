package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.OrderMapper;
import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.dto.OrderDto;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.repository.OrderRepository;
import com.shichko.deliveryservice.model.service.OrderService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController extends CommonController<Order, OrderDto, OrderRepository, OrderService, OrderMapper> {

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
    }

    @PutMapping("/updateState")
    public void update(@RequestParam("id") long id, @RequestParam("state") String state) throws DeliveryServiceException {
        service.updateStateById(id, state);
    }
}