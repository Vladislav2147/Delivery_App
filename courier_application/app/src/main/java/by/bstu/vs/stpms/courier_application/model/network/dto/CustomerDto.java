package by.bstu.vs.stpms.courier_application.model.network.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class CustomerDto extends AbstractDto {
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private Collection<Long> ordersId;
}
