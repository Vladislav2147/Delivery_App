package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.bstu.vs.stpms.courier_application.model.database.entity.Role;
import by.bstu.vs.stpms.courier_application.model.database.entity.User;
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole;

@Dao
public abstract class UserDao extends AbstractDao<User> {

    @Override
    public void insert(User user) {
        Set<Role> roleSet = user.getRoles();
        insertWithoutRoles(user);
        user = findByEmail(user.getEmail());
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role: roleSet) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            userRoles.add(userRole);
        }

        insertUserRoles(userRoles);
    }

    @Insert
    public abstract void insertWithoutRoles(User user);
    @Insert
    public abstract void insertUserRoles(List<UserRole> userRoles);

    @Delete
    @Override
    public abstract void delete(User user);
    @Delete
    @Override
    public abstract void update(User user);

    @Override
    public LiveData<User> findById(long id) {
        LiveData<User> userWithoutRoles = findByIdWithoutRole(id);
        LiveData<User> userWithRoles = Transformations.map(userWithoutRoles, user -> {
           if (user != null) {
               user.setRoles(new HashSet<>(getRolesByUserId(user.getId())));
           }
           return user;
        });
        return userWithRoles;
    }
    @Query("SELECT * FROM user WHERE email = :email")
    public abstract User findByEmail(String email);

    @Query("SELECT * FROM user WHERE id = :id")
    public abstract LiveData<User> findByIdWithoutRole(long id);
    @Override
    public LiveData<List<User>> getAll() {
        LiveData<List<User>> usersWithoutRoles = getAllUsersWithoutRoles();
        LiveData<List<User>> usersWithRoles = Transformations.map(usersWithoutRoles, users -> {
            List<User> lambdaUsersWithRoles = new ArrayList<>();
            for (User user: users) {
                if (users != null) {
                    user.setRoles(new HashSet<>(getRolesByUserId(user.getId())));
                }
                lambdaUsersWithRoles.add(user);
            }

            return lambdaUsersWithRoles;
        });
        return usersWithRoles;
    }

    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> getAllUsersWithoutRoles();
    @Query("SELECT * FROM role WHERE id IN (select roleId from user_role where userId = :userId)")
    public abstract List<Role> getRolesByUserId(long userId);

}
