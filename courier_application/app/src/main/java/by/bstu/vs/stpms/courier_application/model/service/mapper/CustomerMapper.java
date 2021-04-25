package by.bstu.vs.stpms.courier_application.model.service.mapper;

import org.mapstruct.Mapper;

import by.bstu.vs.stpms.courier_application.model.database.entity.Customer;
import by.bstu.vs.stpms.courier_application.model.network.dto.CustomerDto;

@Mapper
public interface CustomerMapper {
    CustomerDto entityToDto(Customer entity);
    Customer dtoToEntity(CustomerDto dto);
}
