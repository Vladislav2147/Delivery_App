package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class CustomerDto extends AbstractDto {
    @Size(min = 1, max = 50)
    private String firstName;
    @Size(min = 1, max = 50)
    private String secondName;
    @Email
    private String email;
    @Pattern(regexp = "(^[+]\\d+(?:[ ]\\d+)*)")
    private String phone;
    private Collection<Long> ordersId;
}
