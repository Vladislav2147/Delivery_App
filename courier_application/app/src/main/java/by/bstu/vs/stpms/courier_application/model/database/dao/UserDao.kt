package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.bstu.vs.stpms.courier_application.model.database.dao.AbstractDao;
import by.bstu.vs.stpms.courier_application.model.database.entity.Role;
import by.bstu.vs.stpms.courier_application.model.database.entity.User;
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole;

@Dao
public abstract class UserDao {

    @Insert
    public abstract void insert(User user);
    @Delete
    public abstract void delete(User user);
    @Update
    public abstract void update(User user);

    @Query("SELECT * FROM user WHERE email = :email")
    public abstract LiveData<User> findByEmail(String email);

    @Query("SELECT * FROM user LIMIT 1")
    public abstract LiveData<List<User>> getUser();

    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> getAllUsersWithoutRoles();
    @Query("SELECT * FROM role WHERE id IN (select roleId from user_role where userId = :userId)")
    public abstract List<Role> getRolesByUserId(long userId);

}
