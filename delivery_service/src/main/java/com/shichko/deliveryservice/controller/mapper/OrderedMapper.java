package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.OrderedDto;
import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Ordered;
import com.shichko.deliveryservice.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderedMapper extends CommonMapper<Ordered, OrderedDto> {
    @Mapping(target = "orderByOrderId", source = "orderId", qualifiedByName = "idToOrder")
    @Mapping(target = "productByProductId", source = "productId", qualifiedByName = "idToProduct")
    Ordered dtoToEntity(OrderedDto dto);
    @Mapping(target = "orderId", source = "orderByOrderId", qualifiedByName = "orderToId")
    @Mapping(target = "productId", source = "productByProductId", qualifiedByName = "productToId")
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

    @Named("productToId")
    default long productToId(Product product) {
        return product.getId();
    }

    @Named("idToProduct")
    default Product idToProduct(long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

}
