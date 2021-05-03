package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Customer

@Dao
abstract class CustomerDao : AbstractDao<Customer>() {

    @Query("DELETE FROM customer WHERE 1=1")
    abstract override suspend fun truncate()

    @Query("SELECT * FROM customer WHERE id = :id")
    abstract override suspend fun findById(id: Long): Customer?

    @Query("SELECT * FROM customer")
    abstract override suspend fun getAll(): List<Customer>
}