package by.bstu.vs.stpms.courier_application.model.network.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

public class OrderDto extends AbstractDto {
    private String address;
    private String info;
    private String state;
    private CustomerDto customer;
    private Long courierId;
    private Collection<OrderedDto> ordered;

    private Timestamp orderedAt;
    private Timestamp deliveredAt;
    private Timestamp preferredRangeStart;
    private Timestamp preferredRangeEnd;

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

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public Collection<OrderedDto> getOrdered() {
        return ordered;
    }

    public void setOrdered(Collection<OrderedDto> ordered) {
        this.ordered = ordered;
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
