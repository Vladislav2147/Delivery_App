package com.shichko.delivery_service.model.dto;

import java.util.Map;

public class OrderDto extends AbstractDto {
    private String address;
    private String info;
    private String state;
    private long customerId;
    private long courierId;
    private Map<Long, Integer> productIdToAmount;
}
