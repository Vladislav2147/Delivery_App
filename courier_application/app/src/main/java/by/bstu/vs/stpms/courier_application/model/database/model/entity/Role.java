package by.bstu.vs.stpms.courier_application.model.database.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "role")
public class Role {
    @PrimaryKey
    private long id;
    private String name;
}
