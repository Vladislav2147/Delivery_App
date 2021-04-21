package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "user_role", primaryKeys = {"userId", "roleId"})
public class UserRole implements Serializable {
    private long userId;
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
