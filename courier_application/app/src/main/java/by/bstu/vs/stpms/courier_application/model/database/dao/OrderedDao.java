package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Order;
import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered;

@Dao
public abstract class OrderedDao extends AbstractDao<Ordered>  {
    @Override
    @Query("SELECT * FROM ordered WHERE id = :id")
    public abstract LiveData<Ordered> findById(long id);

    @Override
    @Query("SELECT * FROM ordered")
    public abstract LiveData<List<Ordered>> getAll();
}
