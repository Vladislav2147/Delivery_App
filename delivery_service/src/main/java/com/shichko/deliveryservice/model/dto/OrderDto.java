package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class OrderDto extends AbstractDto {
    private String address;
    private String info;
    private String state;
    private long customerId;
    private long courierId;
    private Collection<Long> orderedIds;
}
