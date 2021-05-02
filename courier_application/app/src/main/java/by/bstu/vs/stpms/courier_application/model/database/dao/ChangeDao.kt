package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Change

@Dao
abstract class ChangeDao : AbstractDao<Change>() {
    @Query("SELECT * FROM changes WHERE id = :id")
    abstract override suspend fun findById(id: Long): Change?

    @Query("SELECT * FROM changes")
    abstract override suspend fun getAll(): List<Change>
    @Query("UPDATE changes SET isUpToDate = 1 WHERE tableName = :tableName AND itemId = :itemId")
    abstract fun setUpToDate(tableName: String, itemId: Long)
}