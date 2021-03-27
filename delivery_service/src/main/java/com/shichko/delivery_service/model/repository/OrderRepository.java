package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CommonRepository<Order> {
}
