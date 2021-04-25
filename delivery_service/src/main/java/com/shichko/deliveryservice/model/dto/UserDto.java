package com.shichko.deliveryservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Data
public class UserDto extends AbstractDto {
    @Size(min = 1, max = 50)
    private String firstName;
    @Size(min = 1, max = 50)
    private String secondName;
    @Email
    private String email;
    @Pattern(regexp = "^\\+375 \\((17|29|33|44)\\) [0-9]{3}-[0-9]{2}-[0-9]{2}$")
    private String phone;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String confirmPassword;
    private Collection<Long> ordersId;
    private Set<RoleDto> roles;
}
