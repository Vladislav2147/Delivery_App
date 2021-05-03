package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.*

@Dao
abstract class RoleDao : AbstractDao<Role>() {

    @Query("DELETE FROM role WHERE 1=1")
    abstract override suspend fun truncate()

    @Query("SELECT * FROM role WHERE id = :id")
    abstract override suspend fun findById(id: Long): Role?

    @Query("SELECT * FROM role")
    abstract override suspend fun getAll(): List<Role>
}