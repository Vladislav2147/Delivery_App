package com.shichko.delivery_service.model.dto;

import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class UserDto extends AbstractDto {
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private Collection<Long> ordersId;
    private Set<Long> roles;
}
