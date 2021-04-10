package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.RoleDto;
import com.shichko.deliveryservice.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends CommonMapper<Role, RoleDto> {
    RoleDto entityToDto(Role entity);
    Role dtoToEntity(RoleDto dto);
}
