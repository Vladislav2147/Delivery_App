package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Order;
import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered;
import by.bstu.vs.stpms.courier_application.model.database.entity.Product;

@Dao
public abstract class OrderedDao extends AbstractDao<Ordered>  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<Ordered> ordereds);

    @Override
    @Query("SELECT * FROM ordered WHERE id = :id")
    public abstract Ordered findById(long id);

    @Override
    @Query("SELECT * FROM ordered")
    public abstract List<Ordered> getAll();

    @Query("SELECT * FROM ordered WHERE orderId = :orderId")
    public abstract List<Ordered> findByOrderId(long orderId);
}
