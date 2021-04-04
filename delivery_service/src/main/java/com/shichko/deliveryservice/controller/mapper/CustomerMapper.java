package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.CustomerDto;
import com.shichko.deliveryservice.model.entity.Customer;
import com.shichko.deliveryservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends CommonMapper<Customer, CustomerDto> {
    @Mapping(target = "ordersById", source = "ordersId", qualifiedByName = "idToOrders")
    Customer dtoToEntity(CustomerDto dto);
    @Mapping(target = "ordersId", source = "ordersById", qualifiedByName = "ordersToId")
    CustomerDto entityToDto(Customer entity);

    @Named("ordersToId")
    default long ordersToId(Order order) {
        return order.getId();
    }

    @Named("idToOrders")
    default Order idToOrders(long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

}
