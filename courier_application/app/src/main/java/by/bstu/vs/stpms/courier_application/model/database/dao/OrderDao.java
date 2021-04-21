package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Order;

public abstract class OrderDao extends AbstractDao<Order> {
    @Insert
    @Override
    public abstract void insert(Order order);
    @Delete
    @Override
    public abstract void delete(Order order);
    @Delete
    @Override
    public abstract void update(Order order);
    @Query("SELECT * FROM orders WHERE id = :id")
    @Override
    public abstract LiveData<Order> findById(long id);
    @Query("SELECT * FROM orders")
    @Override
    public abstract LiveData<List<Order>> getAll();
}
