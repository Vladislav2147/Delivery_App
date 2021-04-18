package by.bstu.vs.stpms.courier_application.model.database.model.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Collection;

import lombok.Data;

@Data
@Entity(
        tableName = "order",
        foreignKeys = {
                @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "courierId")
        }
)
public class Order {
    @PrimaryKey
    private long id;
    private String address;
    private String info;
    private String state;
    private Long customerId;
    private Long courierId;
    private Collection<Long> orderedsId;
    private Timestamp orderedAt;
    private Timestamp deliveredAt;
}
