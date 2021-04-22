package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType;

@Entity(tableName = "role")
public class Role extends AbstractEntity implements Serializable {
    private String name;

    public Role() {
    }

    public Role(RoleType roleType) {
        this.setId(roleType.getId());
        this.name = roleType.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
