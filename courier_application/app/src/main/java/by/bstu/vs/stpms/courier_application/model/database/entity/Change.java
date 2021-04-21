package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Calendar;

import by.bstu.vs.stpms.courier_application.model.database.entity.converters.CalendarConverter;

@Entity(tableName = "changes")
@TypeConverters(CalendarConverter.class)
public class Change extends AbstractEntity implements Serializable {
    private String tableName;
    private long itemId;
    private String operation;
    private Calendar timeStamp;
    private boolean isUpToDate;

    public Change(String tableName, long itemId, String operation, Calendar timeStamp) {
        this.tableName = tableName;
        this.itemId = itemId;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }

    public Change() {
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
