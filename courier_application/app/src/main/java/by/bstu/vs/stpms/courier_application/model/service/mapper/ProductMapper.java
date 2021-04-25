package by.bstu.vs.stpms.courier_application.model.service.mapper;

import org.mapstruct.Mapper;

import by.bstu.vs.stpms.courier_application.model.database.entity.Product;
import by.bstu.vs.stpms.courier_application.model.database.entity.User;
import by.bstu.vs.stpms.courier_application.model.network.dto.ProductDto;
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto;

@Mapper
public interface ProductMapper {
    ProductDto entityToDto(Product entity);
    Product dtoToEntity(ProductDto dto);
}
