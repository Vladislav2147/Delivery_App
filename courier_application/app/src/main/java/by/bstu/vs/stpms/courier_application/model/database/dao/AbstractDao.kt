package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import by.bstu.vs.stpms.courier_application.model.database.entity.AbstractEntity

abstract class AbstractDao<E : AbstractEntity> {
    @Insert
    abstract fun insert(item: E)
    @Delete
    abstract fun delete(item: E)
    @Delete
    abstract fun deleteAll(items: Collection<E>)
    @Update
    abstract fun update(item: E)
    abstract suspend fun findById(id: Long): E?
    abstract suspend fun getAll(): List<E>
}