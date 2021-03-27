package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CommonRepository<Product> {
}
