package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Product;
import by.bstu.vs.stpms.courier_application.model.database.entity.Role;

@Dao
public abstract class RoleDao extends AbstractDao<Role> {
    @Override
    @Query("SELECT * FROM role WHERE id = :id")
    public abstract LiveData<Role> findById(long id);
    @Override
    @Query("SELECT * FROM role")
    public abstract LiveData<List<Role>> getAll();
}
