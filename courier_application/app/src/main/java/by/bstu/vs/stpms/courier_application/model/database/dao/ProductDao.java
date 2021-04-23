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
    @Override
    @Query("SELECT * FROM product WHERE id = :id")
    public abstract LiveData<Product> findById(long id);
    @Override
    @Query("SELECT * FROM product")
    public abstract LiveData<List<Product>> getAll();
}
