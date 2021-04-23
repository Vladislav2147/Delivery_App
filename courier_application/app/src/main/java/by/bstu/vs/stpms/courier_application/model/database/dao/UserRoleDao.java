package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole;

@Dao
public abstract class UserRoleDao {
    @Insert
    public abstract void insert(UserRole userRole);
    @Update
    public abstract void update(UserRole userRole);
    @Delete
    public abstract void delete(UserRole userRole);
    @Query("SELECT * FROM user_role WHERE userId = :userId")
    public abstract LiveData<List<UserRole>> getUserRolesByUserId(long userId);
}
