package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.model.entity.AbstractEntity;
import com.shichko.deliveryservice.model.repository.CommonRepository;
import com.shichko.deliveryservice.model.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class CommonController<E extends AbstractEntity, R extends CommonRepository<E>, S extends CrudService<E, R>> {
    protected final S service;

    @Autowired
    public CommonController(S service) {
        this.service = service;
    }

    @GetMapping
    public List<E> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<E> getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public void save(E entity) {
        service.add(entity);
    }

    @PutMapping
    public void update(E newEntity, E entityFromDb) {
        service.update(newEntity, entityFromDb);
    }

    @DeleteMapping
    public void delete(E entity) {
        service.delete(entity);
    }

}
