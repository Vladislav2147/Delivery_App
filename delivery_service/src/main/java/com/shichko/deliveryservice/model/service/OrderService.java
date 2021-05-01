package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.enums.OrderState;
import com.shichko.deliveryservice.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService extends CrudService<Order, OrderRepository> {
    @Autowired
    public OrderService(OrderRepository repository) {
        super(repository);
    }

    @Transactional
    public void updateStateById(long id, String newState) throws DeliveryServiceException {
        try {
            repository.updateStateById(id, OrderState.valueOf(newState));
        } catch (IllegalArgumentException e) {
            throw new DeliveryServiceException("Invalid state value: " + newState, e);
        }
    }

    public List<Order> getAvailableOrders() {
        return repository.getAvailableOrders();
    }

    public List<Order> getActiveOrders(long courierId) {
        return  repository.getActiveOrdersByCourierId(courierId);
    }
}