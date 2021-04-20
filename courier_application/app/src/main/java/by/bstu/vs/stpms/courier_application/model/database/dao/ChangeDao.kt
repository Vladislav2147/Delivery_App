package by.bstu.vs.stpms.courier_application.model.database.dao

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import by.bstu.vs.stpms.courier_application.model.database.entity.Change

@Dao
interface ChangeDao {
    @Query("SELECT * from changes")
    fun select(): LiveData<List<Change>>

    @Insert
    fun insert(change: Change?)
}