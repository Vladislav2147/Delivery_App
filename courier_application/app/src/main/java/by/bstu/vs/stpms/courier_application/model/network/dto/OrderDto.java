package by.bstu.vs.stpms.courier_application.model.network.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;

@Data
public class OrderDto extends AbstractDto {
    private String address;
    private String info;
    private String state;
    private CustomerDto customer;
    private Long courierId;
    private Collection<OrderedDto> ordered;
    private Timestamp orderedAt;
    private Timestamp deliveredAt;
}
