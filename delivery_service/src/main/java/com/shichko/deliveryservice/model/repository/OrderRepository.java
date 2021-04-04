package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CommonRepository<Order> {
}
