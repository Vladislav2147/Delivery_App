package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.*

@Dao
abstract class OrderDao : AbstractDao<Order>() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(orders: List<Order>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertWithReplace(order: Order)
    @Query("SELECT * FROM orders WHERE id = :id")
    abstract override suspend fun findById(id: Long): Order?

    @Query("SELECT * FROM orders")
    abstract override suspend fun getAll(): List<Order>
}