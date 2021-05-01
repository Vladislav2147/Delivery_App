package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Order;

@Dao
public abstract class OrderDao extends AbstractDao<Order>  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<Order> orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertWithReplace(Order order);

    @Override
    @Query("SELECT * FROM orders WHERE id = :id")
    public abstract LiveData<Order> findById(long id);

    @Override
    @Query("SELECT * FROM orders")
    public abstract LiveData<List<Order>> getAll();
}
