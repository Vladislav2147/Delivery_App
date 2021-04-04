package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ProductDto extends AbstractDto {
    private String name;
    private double weight;
    private double price;
    private Collection<Long> orderedIds;
}
