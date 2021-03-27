package com.shichko.delivery_service.model.dto;

import lombok.Data;

@Data
public class ProductDto extends AbstractDto {
    private String name;
    private double weight;
    private double price;
}
