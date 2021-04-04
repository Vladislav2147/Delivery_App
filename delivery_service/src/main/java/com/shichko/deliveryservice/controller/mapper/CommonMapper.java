package com.shichko.deliveryservice.controller.mapper;

import com.shichko.deliveryservice.model.dto.AbstractDto;
import com.shichko.deliveryservice.model.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public interface CommonMapper<E extends AbstractEntity, D extends AbstractDto> {

    D entityToDto(E entity);

    E dtoToEntity(D dto);

    default List<E> dtosToEntities(List<D> dtos) {
        List<E> entities = new ArrayList<>();
        for(D dto: dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
    default List<D> entitiesToDtos(List<E> entities) {
        List<D> dtos = new ArrayList<>();
        for(E entity: entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}