package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole

@Dao
abstract class UserRoleDao {
    @Insert
    abstract fun insert(userRole: UserRole?)
    @Update
    abstract fun update(userRole: UserRole?)
    @Delete
    abstract fun delete(userRole: UserRole?)
    @Query("SELECT * FROM role WHERE role.id IN (SELECT roleId FROM user_role WHERE userId = :userId)")
    abstract fun getUserRolesByUserId(userId: Long): LiveData<List<Role?>?>?
}