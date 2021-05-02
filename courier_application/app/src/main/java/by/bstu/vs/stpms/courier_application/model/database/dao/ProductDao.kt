package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.*
import by.bstu.vs.stpms.courier_application.model.database.entity.Product

@Dao
abstract class ProductDao : AbstractDao<Product>() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(products: List<Product>)
    @Query("SELECT * FROM product WHERE id = :id")
    abstract override suspend fun findById(id: Long): Product?

    @Query("SELECT * FROM product")
    abstract override suspend fun getAll(): List<Product>
}