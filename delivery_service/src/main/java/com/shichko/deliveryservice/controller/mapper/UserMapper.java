package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.AbstractEntity;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Role;
import com.shichko.deliveryservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends CommonMapper<User, UserDto> {

    @Mapping(source = "ordersById", target = "ordersId", qualifiedByName = "orderToOrderId")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToId")
    UserDto entityToDto(User entity);

    @Mapping(source = "ordersId", target = "ordersById", qualifiedByName = "orderIdToOrder")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "idToRole")
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

    @Named("roleToId")
    default long roleToId(Role role) {
        return role.getId();
    }

    @Named("idToRole")
    default Role idToRole(long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
