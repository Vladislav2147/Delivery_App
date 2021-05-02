package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.Collection;
import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.AbstractEntity;

public abstract class AbstractDao<E extends AbstractEntity> {
    @Insert
    public abstract void insert(E item);
    @Delete
    public abstract void delete(E item);
    @Delete
    public abstract void deleteAll(Collection<E> items);
    @Update
    public abstract void update(E item);
    public abstract E findById(long id);
    public abstract List<E> getAll();
}
