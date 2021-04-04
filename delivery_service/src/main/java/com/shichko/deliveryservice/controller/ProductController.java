package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.ProductMapper;
import com.shichko.deliveryservice.model.dto.ProductDto;
import com.shichko.deliveryservice.model.entity.Product;
import com.shichko.deliveryservice.model.repository.ProductRepository;
import com.shichko.deliveryservice.model.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController extends CommonController<Product, ProductDto, ProductRepository, ProductService, ProductMapper> {

    public ProductController(ProductService service, ProductMapper mapper) {
        super(service, mapper);
    }
}
