package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.OrderedDto;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Ordered;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderedMapper extends CommonMapper<Ordered, OrderedDto> {
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    Ordered dtoToEntity(OrderedDto dto);
    @Mapping(target = "orderId", source = "order", qualifiedByName = "orderToId")
    OrderedDto entityToDto(Ordered entity);

    @Named("orderToId")
    default long orderToId(Order order) {
        return order.getId();
    }

    @Named("idToOrder")
    default Order idToOrder(long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
