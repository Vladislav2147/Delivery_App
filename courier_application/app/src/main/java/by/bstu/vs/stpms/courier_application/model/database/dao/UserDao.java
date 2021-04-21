package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.User;

public abstract class UserDao extends AbstractDao<User> {
    @Insert
    @Override
    public abstract void insert(User user);
    @Delete
    @Override
    public abstract void delete(User user);
    @Delete
    @Override
    public abstract void update(User user);
    @Query("SELECT * FROM user WHERE id = :id")
    @Override
    public abstract LiveData<User> findById(long id);
    @Query("SELECT * FROM user")
    @Override
    public abstract LiveData<List<User>> getAll();
}
