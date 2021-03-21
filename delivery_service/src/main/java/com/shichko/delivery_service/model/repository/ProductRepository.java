package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
