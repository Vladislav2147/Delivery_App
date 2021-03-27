package com.shichko.delivery_service.model.service;

import com.shichko.delivery_service.model.entity.AbstractEntity;
import com.shichko.delivery_service.model.repository.CommonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<E extends AbstractEntity, R extends CommonRepository<E>> {
    protected final R repository;

    @Autowired
    public CrudService(R repository) {
        this.repository = repository;
    }

    public void add(E entity) {
        repository.save(entity);
    }

    public void update(E newEntity, E entityFromDb) {
        BeanUtils.copyProperties(newEntity, entityFromDb, "id");
        repository.save(entityFromDb);
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public Optional<E> findById(long id) {
        return repository.findById(id);
    }
}
