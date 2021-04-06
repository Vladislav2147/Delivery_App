package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.OrderDto;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderedMapper.class})
public interface OrderMapper extends CommonMapper<Order, OrderDto> {
    @Mapping(target = "courier", source = "courierId", qualifiedByName = "idToCourier")
    Order dtoToEntity(OrderDto dto);
    @Mapping(target = "courierId", source = "courier", qualifiedByName = "courierToId")
    OrderDto entityToDto(Order entity);

    @Named("courierToId")
    default Long courierToId(User courier) {
        return courier != null ? courier.getId() : null;
    }

    @Named("idToCourier")
    default User idToCourier(Long id) {
        if (id == null) {
            return null;
        }
        User courier = new User();
        courier.setId(id);
        return courier;
    }

}
