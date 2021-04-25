package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;

@Data
public class OrderDto extends AbstractDto {
    @Size(max = 255)
    private String address;
    @Size(max = 255)
    private String info;
    private String state;
    @Valid
    private CustomerDto customer;
    private Long courierId;
    private Collection<OrderedDto> ordered;
    private Timestamp orderedAt;
    private Timestamp deliveredAt;
    private Timestamp preferredRangeStart;
    private Timestamp preferredRangeEnd;
}
