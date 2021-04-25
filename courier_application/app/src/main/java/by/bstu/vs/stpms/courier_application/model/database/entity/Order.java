package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.converters.TimestampConverter;

@Entity(
        tableName = "orders",
        foreignKeys = {
                @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "courierId")
        }
)
@TypeConverters(TimestampConverter.class)
public class Order extends AbstractEntity implements Serializable {
    private String address;
    private String info;
    private String state;
    private Long customerId;
    private Long courierId;

    private Timestamp orderedAt;
    private Timestamp deliveredAt;
    private Timestamp preferredRangeStart;
    private Timestamp preferredRangeEnd;

    @Ignore
    private List<Ordered> ordereds;
    @Ignore
    private Customer customer;
    @Ignore
    private User courier;

    public Order() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public List<Ordered> getOrdereds() {
        return ordereds;
    }

    public void setOrdereds(List<Ordered> ordereds) {
        this.ordereds = ordereds;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public Timestamp getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Timestamp orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Timestamp getPreferredRangeStart() {
        return preferredRangeStart;
    }

    public void setPreferredRangeStart(Timestamp preferredRangeStart) {
        this.preferredRangeStart = preferredRangeStart;
    }

    public Timestamp getPreferredRangeEnd() {
        return preferredRangeEnd;
    }

    public void setPreferredRangeEnd(Timestamp preferredRangeEnd) {
        this.preferredRangeEnd = preferredRangeEnd;
    }
}
