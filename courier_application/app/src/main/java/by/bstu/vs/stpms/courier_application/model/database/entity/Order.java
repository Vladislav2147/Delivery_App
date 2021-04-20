package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

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
    //TODO fix it
    private String orderedAt;
    private String deliveredAt;

    @Ignore
    private List<Ordered> ordereds;

    public Order() {
    }

    public long getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public String getInfo() {
        return this.info;
    }

    public String getState() {
        return this.state;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Long getCourierId() {
        return this.courierId;
    }

    public String getOrderedAt() {
        return this.orderedAt;
    }

    public String getDeliveredAt() {
        return this.deliveredAt;
    }

    public List<Ordered> getOrdereds() {
        return this.ordereds;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public void setOrdereds(List<Ordered> ordereds) {
        this.ordereds = ordereds;
    }

    public String toString() {
        return "Order(id=" + this.getId() + ", address=" + this.getAddress() + ", info=" + this.getInfo() + ", state=" + this.getState() + ", customerId=" + this.getCustomerId() + ", courierId=" + this.getCourierId() + ", orderedAt=" + this.getOrderedAt() + ", deliveredAt=" + this.getDeliveredAt() + ", ordereds=" + this.getOrdereds() + ")";
    }
}
