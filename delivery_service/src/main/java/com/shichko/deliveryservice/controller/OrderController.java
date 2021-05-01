package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.OrderMapper;
import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.dto.OrderDto;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.User;
import com.shichko.deliveryservice.model.repository.OrderRepository;
import com.shichko.deliveryservice.model.service.OrderService;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController extends CommonController<Order, OrderDto, OrderRepository, OrderService, OrderMapper> {

    @Autowired
    private UserService userService;

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
    }

    @Override
    @PostMapping
    public void save(@Valid OrderDto dto) {
        //TODO notify client by email and generate soap endpoint
        super.save(dto);
    }

    @PutMapping("/updateState")
    public void update(@RequestParam("id") long id, @RequestParam("state") String state) throws DeliveryServiceException {
        service.updateStateById(id, state);
    }

    @GetMapping("/available")
    public List<OrderDto> getAvailableOrders() {
        return mapper.entitiesToDtos(service.getAvailableOrders());
    }

    @GetMapping("/accept/{id}")
    public void acceptOrder(@PathVariable("id") Long id) throws DeliveryServiceException {
        Optional<Order> orderOptional = service.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getCourier() == null) {
                UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User currentUser = (User)userService.loadUserByUsername(details.getUsername());
                order.setCourier(currentUser);
                service.save(order);
            } else {
                throw new DeliveryServiceException("Order " + order.getId() + " already accepted by courier " + order.getCourier().getId());
            }
        } else {
            throw new DeliveryServiceException("Order with id = " + id + " not found");
        }
    }

    @GetMapping("/decline/{id}")
    public void declineOrder(@PathVariable("id") Long id) throws DeliveryServiceException {
        Optional<Order> orderOptional = service.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            if (order.getCourier() == null) {
                throw new DeliveryServiceException("Unable to decline order " + order.getId() + " that hasn't courier");
            }

            UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = (User)userService.loadUserByUsername(details.getUsername());

            if (order.getCourier().getId() == currentUser.getId()) {
                order.setCourier(null);
                service.save(order);
            } else {
                throw new DeliveryServiceException("Order " + order.getId() + " cannot be declined cause belongs to courier: " + order.getCourier().getId());
            }
        } else {
            throw new DeliveryServiceException("Order with id = " + id + " not found");
        }
    }

    @GetMapping("active")
    public List<OrderDto> getActiveOrders() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = ((User)userService.loadUserByUsername(details.getUsername())).getId();
        List<OrderDto> orders = mapper.entitiesToDtos(new ArrayList<>(service.getActiveOrders(currentUserId)));
        return orders;
    }
}