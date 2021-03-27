package com.shichko.delivery_service.controller;

import com.shichko.delivery_service.model.entity.Product;
import com.shichko.delivery_service.model.repository.ProductRepository;
import com.shichko.delivery_service.model.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController extends CommonController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }
}
