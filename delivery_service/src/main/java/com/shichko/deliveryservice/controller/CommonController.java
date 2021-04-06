package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.CommonMapper;
import com.shichko.deliveryservice.model.dto.AbstractDto;
import com.shichko.deliveryservice.model.entity.AbstractEntity;
import com.shichko.deliveryservice.model.repository.CommonRepository;
import com.shichko.deliveryservice.model.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class CommonController<E extends AbstractEntity, D extends AbstractDto, R extends CommonRepository<E>, S extends CrudService<E, R>, M extends CommonMapper<E, D>> {
    protected final S service;
    protected final M mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public CommonController(S service, M mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<D> getAll() {
        return mapper.entitiesToDtos(service.getAll());
    }

    @GetMapping("/{id}")
    public Optional<D> getById(@PathVariable("id") Long id) {
        Optional<E> entity = service.findById(id);
        return entity.map(mapper::entityToDto);
    }

    @PostMapping
    public void save(@RequestBody D dto) {
        service.add(mapper.dtoToEntity(dto));
    }

    @PutMapping
    public void update(@RequestBody D dto) {
        service.update(mapper.dtoToEntity(dto), dto.getId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }

}
