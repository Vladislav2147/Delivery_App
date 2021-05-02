package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Customer;
import by.bstu.vs.stpms.courier_application.model.database.entity.Product;

@Dao
public abstract class CustomerDao extends AbstractDao<Customer> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertWithReplace(Customer customer);

    @Override
    @Query("SELECT * FROM customer WHERE id = :id")
    public abstract Customer findById(long id);
    @Override
    @Query("SELECT * FROM customer")
    public abstract List<Customer> getAll();
}
