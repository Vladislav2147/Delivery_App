package com.shichko.deliveryservice.model.dto;

import java.util.Collection;

public class OrderDto extends AbstractDto {
    private String address;
    private String info;
    private String state;
    private long customerId;
    private long courierId;
    private Collection<Long> orderedIds;
}
