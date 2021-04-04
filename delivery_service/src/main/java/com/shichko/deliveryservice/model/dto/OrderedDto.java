package com.shichko.deliveryservice.model.dto;

import lombok.Data;

@Data
public class OrderedDto extends AbstractDto {
    private int amount;
    private long orderId;
    private long productId;
}
