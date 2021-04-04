package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.model.entity.Product;
import com.shichko.deliveryservice.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<Product, ProductRepository> {
    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
    }
}
