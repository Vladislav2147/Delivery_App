package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.PrimaryKey;

public abstract class AbstractEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
