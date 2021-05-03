package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.*

@Dao
abstract class OrderDao : AbstractDao<Order>() {

    @Query("DELETE FROM orders WHERE 1=1")
    abstract override suspend fun truncate()

    @Query("SELECT * FROM orders WHERE id = :id")
    abstract override suspend fun findById(id: Long): Order?

    @Query("SELECT * FROM orders")
    abstract override suspend fun getAll(): List<Order>
}