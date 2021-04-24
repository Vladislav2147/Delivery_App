package by.bstu.vs.stpms.courier_application.model.service.mapper;

import org.mapstruct.Mapper;

import by.bstu.vs.stpms.courier_application.model.database.entity.User;
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {
    UserDto entityToDto(User entity);
    User dtoToEntity(UserDto dto);
}
