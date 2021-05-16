package com.shichko.deliveryservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDto {
    private long courierId;
    private int deliveredOrdersCount;
    private int deliveredInTimeCount;
    private double deliveredTotalPrice;
    private int deliveredProductsCount;
}
