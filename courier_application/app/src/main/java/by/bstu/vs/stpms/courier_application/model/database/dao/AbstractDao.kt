package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.AbstractEntity
import by.bstu.vs.stpms.courier_application.model.database.entity.Customer

abstract class AbstractDao<E : AbstractEntity> {
    @Insert
    abstract suspend fun insert(item: E)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<E>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertWithReplace(item: E)

    @Delete
    abstract suspend fun delete(item: E)
    @Delete
    abstract suspend fun deleteAll(items: Collection<E>)

    @Update
    abstract suspend fun update(item: E)
    abstract suspend fun findById(id: Long): E?
    abstract suspend fun getAll(): List<E>
    abstract suspend fun truncate()
}