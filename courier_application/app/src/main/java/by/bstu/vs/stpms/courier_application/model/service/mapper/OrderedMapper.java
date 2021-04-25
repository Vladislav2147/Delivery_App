package by.bstu.vs.stpms.courier_application.model.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered;
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderedDto;

@Mapper(uses = ProductMapper.class)
public interface OrderedMapper {

    OrderedDto entityToDto(Ordered entity);
    Ordered dtoToEntity(OrderedDto dto);

    @AfterMapping
    default void setProductId(OrderedDto source, @MappingTarget Ordered target) {
        target.setProductId(source.getProduct().getId());
    }
}
