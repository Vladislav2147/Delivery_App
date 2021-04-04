package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CommonRepository<Product> {
}
