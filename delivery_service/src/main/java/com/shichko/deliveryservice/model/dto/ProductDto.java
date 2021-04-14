package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class ProductDto extends AbstractDto {
    @Size(min = 1, max = 50)
    private String name;
    @PositiveOrZero
    private double weight;
    @PositiveOrZero
    private double price;
    private Collection<Long> orderedIds;
}
