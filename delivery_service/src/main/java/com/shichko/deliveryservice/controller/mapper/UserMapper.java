package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.AbstractEntity;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends CommonMapper<User, UserDto> {

    @Mapping(source = "orders", target = "ordersId", qualifiedByName = "orderToOrderId")
    UserDto entityToDto(User entity);

    @Mapping(source = "ordersId", target = "orders", qualifiedByName = "orderIdToOrder")
    User dtoToEntity(UserDto dto);

    @Named("orderToOrderId")
    default Collection<Long> orderToOrderId(Collection<Order> orders) {
        return orders.stream().map(AbstractEntity::getId).collect(Collectors.toList());
    }
    @Named("orderIdToOrder")
    default Order orderIdToOrder(long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
