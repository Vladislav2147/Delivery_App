package by.bstu.vs.stpms.courier_application.model.database.dao_weird;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.dao.AbstractDao;
import by.bstu.vs.stpms.courier_application.model.database.entity.Customer;
import by.bstu.vs.stpms.courier_application.model.database.entity.Order;
import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered;
import by.bstu.vs.stpms.courier_application.model.database.entity.Product;

@Dao
public abstract class OrderDao extends AbstractDao<Order> {
    @Insert
    @Override
    public void insert(Order order) {
        insertOrderOnly(order);
        insertCustomer(order.getCustomer());
        order.getOrdered().forEach(ordered ->  {
            insertProduct(ordered.getProduct());
            insertOrdered(ordered);
        });
    }

    @Insert
    public abstract void insertOrderOnly(Order order);

    @Insert
    public abstract void insertOrdered(Ordered ordered);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertProduct(Product product);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCustomer(Customer customer);
    @Override
    public void delete(Order order) {
        deleteOrderedsByOrderId(order.getId());
        deleteByOrderId(order.getId());
    }
    @Query("delete from ordered where orderId = :orderId")
    public abstract void deleteOrderedsByOrderId(long orderId);
    @Query("delete from orders where id = :id")
    public abstract void deleteByOrderId(long id);
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
