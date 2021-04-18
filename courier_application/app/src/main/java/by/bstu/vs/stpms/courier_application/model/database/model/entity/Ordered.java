package by.bstu.vs.stpms.courier_application.model.database.model.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(
        tableName = "ordered",
        foreignKeys = {
                @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "productId"),
                @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId")
        }
)
public class Ordered {
    @PrimaryKey
    private long id;
    private int amount;
    private long orderId;
    private long productId;
}
