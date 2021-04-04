package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.ProductDto;
import com.shichko.deliveryservice.model.entity.Ordered;
import com.shichko.deliveryservice.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper extends CommonMapper<Product, ProductDto> {
    @Mapping(target = "orderedById", source = "orderedIds", qualifiedByName = "idToOrdered")
    Product dtoToEntity(ProductDto dto);
    @Mapping(target = "orderedIds", source = "orderedById", qualifiedByName = "orderedToId")
    ProductDto entityToDto(Product entity);

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
}
