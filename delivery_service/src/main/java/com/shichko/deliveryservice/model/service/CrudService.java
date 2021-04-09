package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.model.entity.AbstractEntity;
import com.shichko.deliveryservice.model.repository.CommonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<E extends AbstractEntity, R extends CommonRepository<E>> {
    protected final R repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public CrudService(R repository) {
        this.repository = repository;
    }

    public void add(E entity) {
        repository.save(entity);
    }

    public void update(E newEntity, long id) {
        repository.findById(id).ifPresent(entityFromDb -> {
            BeanUtils.copyProperties(newEntity, entityFromDb, "id");
            repository.save(entityFromDb);
        });
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public Optional<E> findById(long id) {
        return repository.findById(id);
    }
}
