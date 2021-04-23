package by.bstu.vs.stpms.courier_application.model.repository.mapper;

import org.mapstruct.Mapper;

import by.bstu.vs.stpms.courier_application.model.database.entity.Role;
import by.bstu.vs.stpms.courier_application.model.network.dto.RoleDto;

@Mapper
public interface RoleMapper {
    RoleDto entityToDto(Role entity);
    Role dtoToEntity(RoleDto dto);
}
