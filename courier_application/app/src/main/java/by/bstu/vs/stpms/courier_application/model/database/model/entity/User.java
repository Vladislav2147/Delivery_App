package by.bstu.vs.stpms.courier_application.model.database.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collection;
import java.util.Set;

import lombok.Data;

@Data
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    private long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private String password;
}
