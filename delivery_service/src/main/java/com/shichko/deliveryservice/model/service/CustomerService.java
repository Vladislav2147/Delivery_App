package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.model.entity.Customer;
import com.shichko.deliveryservice.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends CrudService<Customer, CustomerRepository> {
    @Autowired
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}
