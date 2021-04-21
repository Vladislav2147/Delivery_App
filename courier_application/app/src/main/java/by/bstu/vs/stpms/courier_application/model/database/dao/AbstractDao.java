package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.AbstractEntity;

public abstract class AbstractDao<E extends AbstractEntity> {
    public abstract void insert(E item);
    public abstract void delete(E item);
    public abstract void update(E item);
    public abstract LiveData<E> findById(long id);
    public abstract LiveData<List<E>> getAll();
}
