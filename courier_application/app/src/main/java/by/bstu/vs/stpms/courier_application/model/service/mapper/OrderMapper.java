package by.bstu.vs.stpms.courier_application.model.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Order;
import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered;
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto;

@Mapper(uses = {OrderedMapper.class, CustomerMapper.class})
public interface OrderMapper {
    OrderDto entityToDto(Order entity);
    Order dtoToEntity(OrderDto dto);

    List<Order> dtosToEntities(List<OrderDto> dtos);

    @AfterMapping
    default void setCustomerId(OrderDto source, @MappingTarget Order target) {
        target.setCustomerId(source.getCustomer().getId());
    }
}
