package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

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
    private Collection<Ordered> ordered;
    @Ignore
    private Customer customer;

    public double getTotalPrice() {
        double price = 0.0;
        for (Ordered productAmount: ordered) {
            price += productAmount.getAmount() * productAmount.getProduct().getPrice();
        }
        return price;
    }

    public double getTotalWeight() {
        double weight = 0.0;
        for (Ordered productAmount: ordered) {
            weight += productAmount.getAmount() * productAmount.getProduct().getWeight();
        }
        return weight;
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

    public Collection<Ordered> getOrdered() {
        return ordered;
    }

    public void setOrdered(Collection<Ordered> ordered) {
        this.ordered = ordered;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
