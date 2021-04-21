package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Change;

@Dao
public abstract class ChangeDao extends AbstractDao<Change> {
    @Insert
    @Override
    public abstract void insert(Change change);
    @Delete
    @Override
    public abstract void delete(Change change);
    @Delete
    @Override
    public abstract void update(Change change);
    @Query("SELECT * FROM changes WHERE id = :id")
    @Override
    public abstract LiveData<Change> findById(long id);
    @Query("SELECT * FROM changes")
    @Override
    public abstract LiveData<List<Change>> getAll();
}
