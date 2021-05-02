package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.User

@Dao
abstract class UserDao {
    @Insert
    abstract fun insert(user: User)
    @Delete
    abstract fun delete(user: User)
    @Update
    abstract fun update(user: User)
    @Query("SELECT * FROM user WHERE email = :email")
    abstract fun findByEmail(email: String): LiveData<User>

    @get:Query("SELECT * FROM user LIMIT 1")
    abstract val user: LiveData<List<User>?>

    @get:Query("SELECT * FROM user")
    abstract val allUsersWithoutRoles: LiveData<List<User>?>
    @Query("SELECT * FROM role WHERE id IN (select roleId from user_role where userId = :userId)")
    abstract fun getRolesByUserId(userId: Long): List<Role>
}