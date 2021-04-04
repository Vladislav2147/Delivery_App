package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.OrderDto;
import com.shichko.deliveryservice.model.entity.Customer;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Ordered;
import com.shichko.deliveryservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper extends CommonMapper<Order, OrderDto> {
    @Mapping(target = "orderedById", source = "orderedIds", qualifiedByName = "idToOrdered")
    @Mapping(target = "customerByCustomerId", source = "customerId", qualifiedByName = "idToCustomer")
    @Mapping(target = "userByCourierId", source = "courierId", qualifiedByName = "idToCourier")
    Order dtoToEntity(OrderDto dto);
    @Mapping(target = "orderedIds", source = "orderedById", qualifiedByName = "orderedToId")
    @Mapping(target = "customerId", source = "customerByCustomerId", qualifiedByName = "customerToId")
    @Mapping(target = "courierId", source = "userByCourierId", qualifiedByName = "courierToId")
    OrderDto entityToDto(Order entity);

    @Named("orderedToId")
    default long orderedToId(Ordered ordered) {
        return ordered.getId();
    }

    @Named("idToOrdered")
    default Ordered idToOrdered(long id) {
        Ordered ordered = new Ordered();
        ordered.setId(id);
        return ordered;
    }

    @Named("customerToId")
    default long customerToId(Customer customer) {
        return customer.getId();
    }

    @Named("idToCustomer")
    default Customer idToCustomer(long id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("courierToId")
    default long courierToId(User courier) {
        return courier.getId();
    }

    @Named("idToCourier")
    default User idToCourier(long id) {
        User courier = new User();
        courier.setId(id);
        return courier;
    }

}
