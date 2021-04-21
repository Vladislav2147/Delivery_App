package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Product;

@Dao
public abstract class ProductDao extends AbstractDao<Product> {
    @Insert
    @Override
    public abstract void insert(Product product);
    @Delete
    @Override
    public abstract void delete(Product product);
    @Delete
    @Override
    public abstract void update(Product product);
    @Query("SELECT * FROM product WHERE id = :id")
    @Override
    public abstract LiveData<Product> findById(long id);
    @Query("SELECT * FROM product")
    @Override
    public abstract LiveData<List<Product>> getAll();
}
