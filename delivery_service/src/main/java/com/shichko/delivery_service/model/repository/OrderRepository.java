package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
