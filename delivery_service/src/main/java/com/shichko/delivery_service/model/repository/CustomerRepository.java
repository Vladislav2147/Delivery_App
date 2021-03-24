package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Collection<Customer> findFirstByEmailAndFirstName(String email, String firstName);
}
