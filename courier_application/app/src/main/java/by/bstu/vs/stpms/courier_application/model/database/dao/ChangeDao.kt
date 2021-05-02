package by.bstu.vs.stpms.courier_application.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.Change;

@Dao
public abstract class ChangeDao extends AbstractDao<Change> {

    @Query("SELECT * FROM changes WHERE id = :id")
    @Override
    public abstract Change findById(long id);
    @Query("SELECT * FROM changes")
    @Override
    public abstract List<Change> getAll();

    @Query("UPDATE changes SET isUpToDate = 1 WHERE tableName = :tableName AND itemId = :itemId")
    public abstract void setUpToDate(String tableName, long itemId);
}
