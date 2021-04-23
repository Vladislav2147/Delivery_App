package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import by.bstu.vs.stpms.courier_application.model.database.entity.converters.CalendarConverter;

@Entity(
        tableName = "orders",
        foreignKeys = {
                @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "courierId")
        }
)
@TypeConverters(CalendarConverter.class)
public class Order extends AbstractEntity implements Serializable {
    private String address;
    private String info;
    private String state;
    private Long customerId;
    private Long courierId;

    private Calendar orderedAt;
    private Calendar deliveredAt;

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

    public Calendar getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Calendar orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Calendar getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Calendar deliveredAt) {
        this.deliveredAt = deliveredAt;
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
}
