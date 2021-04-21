package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;

import by.bstu.vs.stpms.courier_application.model.database.entity.converters.CalendarConverter;

@Entity(tableName = "changes")
@TypeConverters(CalendarConverter.class)
public class Change {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tableName;
    private long itemId;
    private String operation;
    private Calendar timeStamp;
    private boolean isUpToDate;

    public int getId() {
        return id;
    }

    public Change(String tableName, long itemId, String operation, Calendar timeStamp) {
        this.tableName = tableName;
        this.itemId = itemId;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }

    public Change(int id, String tableName, long itemId, String operation, Calendar timeStamp) {
        this.id = id;
        this.tableName = tableName;
        this.itemId = itemId;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }

    public Change() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Calendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isUpToDate() {
        return isUpToDate;
    }

    public void setUpToDate(boolean upToDate) {
        isUpToDate = upToDate;
    }
}
