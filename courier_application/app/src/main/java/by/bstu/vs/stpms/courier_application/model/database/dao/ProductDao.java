package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Product;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

//    @Query("DELETE FROM product where id = :id")
    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAll();
}
