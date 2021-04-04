package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.model.entity.Customer;
import com.shichko.deliveryservice.model.repository.CustomerRepository;
import com.shichko.deliveryservice.model.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends CommonController<Customer, CustomerRepository, CustomerService> {
    public CustomerController(CustomerService service) {
        super(service);
    }
}
