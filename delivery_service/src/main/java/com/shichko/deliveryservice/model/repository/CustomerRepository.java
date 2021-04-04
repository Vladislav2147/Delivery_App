package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CustomerRepository extends CommonRepository<Customer> {
    Collection<Customer> findFirstByEmailAndFirstName(String email, String firstName);
}
