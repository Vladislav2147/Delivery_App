package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.*

@Dao
abstract class OrderedDao : AbstractDao<Ordered>() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(ordereds: List<Ordered>)
    @Query("SELECT * FROM ordered WHERE id = :id")
    abstract override suspend fun findById(id: Long): Ordered?

    @Query("SELECT * FROM ordered")
    abstract override suspend fun getAll(): List<Ordered>
    @Query("SELECT * FROM ordered WHERE orderId = :orderId")
    abstract fun findByOrderId(orderId: Long): List<Ordered>
}