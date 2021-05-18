package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Ordered;
import com.shichko.deliveryservice.model.entity.enums.OrderState;
import com.shichko.deliveryservice.model.repository.OrderRepository;
import com.shichko.deliveryservice.model.repository.OrderedRepository;
import com.shichko.deliveryservice.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService extends CrudService<Order, OrderRepository> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderedRepository orderedRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository repository) {
        super(repository);
    }

    @Override
    public void add(Order entity) {
        List<Ordered> ordereds = new ArrayList<>(entity.getOrdered());
        entity.setOrdered(null);
        Order addedOrder = repository.save(entity);
        for (Ordered ordered: ordereds) {
            ordered.setOrder(addedOrder);
            ordered.setProduct(productRepository.findById(ordered.getProduct().getId()).get());
            orderedRepository.save(ordered);
        }
        addedOrder.setOrdered(ordereds);
        addedOrder = repository.save(addedOrder);
        Order finalOrder = repository.findById(addedOrder.getId()).get();
        try {
            emailService.sendEmail(finalOrder);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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