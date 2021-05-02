package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Customer

@Dao
abstract class CustomerDao : AbstractDao<Customer>() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWithReplace(customer: Customer)
    @Query("SELECT * FROM customer WHERE id = :id")
    abstract override suspend fun findById(id: Long): Customer?

    @Query("SELECT * FROM customer")
    abstract override suspend fun getAll(): List<Customer>
}