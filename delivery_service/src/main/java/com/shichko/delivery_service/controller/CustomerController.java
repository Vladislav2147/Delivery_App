package com.shichko.delivery_service.controller;

import com.shichko.delivery_service.model.entity.Customer;
import com.shichko.delivery_service.model.repository.CustomerRepository;
import com.shichko.delivery_service.model.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends CommonController<Customer, CustomerRepository, CustomerService> {
    public CustomerController(CustomerService service) {
        super(service);
    }
}
