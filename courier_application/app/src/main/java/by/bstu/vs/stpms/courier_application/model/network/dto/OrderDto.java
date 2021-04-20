package by.bstu.vs.stpms.courier_application.model.network.dto;

import java.sql.Timestamp;
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

    public OrderDto() {
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

    public CustomerDto getCustomer() {
        return this.customer;
    }

    public Long getCourierId() {
        return this.courierId;
    }

    public Collection<OrderedDto> getOrdered() {
        return this.ordered;
    }

    public Timestamp getOrderedAt() {
        return this.orderedAt;
    }

    public Timestamp getDeliveredAt() {
        return this.deliveredAt;
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

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public void setOrdered(Collection<OrderedDto> ordered) {
        this.ordered = ordered;
    }

    public void setOrderedAt(Timestamp orderedAt) {
        this.orderedAt = orderedAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderDto))
            return false;
        final OrderDto other = (OrderDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address))
            return false;
        final Object this$info = this.getInfo();
        final Object other$info = other.getInfo();
        if (this$info == null ? other$info != null : !this$info.equals(other$info)) return false;
        final Object this$state = this.getState();
        final Object other$state = other.getState();
        if (this$state == null ? other$state != null : !this$state.equals(other$state))
            return false;
        final Object this$customer = this.getCustomer();
        final Object other$customer = other.getCustomer();
        if (this$customer == null ? other$customer != null : !this$customer.equals(other$customer))
            return false;
        final Object this$courierId = this.getCourierId();
        final Object other$courierId = other.getCourierId();
        if (this$courierId == null ? other$courierId != null : !this$courierId.equals(other$courierId))
            return false;
        final Object this$ordered = this.getOrdered();
        final Object other$ordered = other.getOrdered();
        if (this$ordered == null ? other$ordered != null : !this$ordered.equals(other$ordered))
            return false;
        final Object this$orderedAt = this.getOrderedAt();
        final Object other$orderedAt = other.getOrderedAt();
        if (this$orderedAt == null ? other$orderedAt != null : !this$orderedAt.equals(other$orderedAt))
            return false;
        final Object this$deliveredAt = this.getDeliveredAt();
        final Object other$deliveredAt = other.getDeliveredAt();
        if (this$deliveredAt == null ? other$deliveredAt != null : !this$deliveredAt.equals(other$deliveredAt))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $info = this.getInfo();
        result = result * PRIME + ($info == null ? 43 : $info.hashCode());
        final Object $state = this.getState();
        result = result * PRIME + ($state == null ? 43 : $state.hashCode());
        final Object $customer = this.getCustomer();
        result = result * PRIME + ($customer == null ? 43 : $customer.hashCode());
        final Object $courierId = this.getCourierId();
        result = result * PRIME + ($courierId == null ? 43 : $courierId.hashCode());
        final Object $ordered = this.getOrdered();
        result = result * PRIME + ($ordered == null ? 43 : $ordered.hashCode());
        final Object $orderedAt = this.getOrderedAt();
        result = result * PRIME + ($orderedAt == null ? 43 : $orderedAt.hashCode());
        final Object $deliveredAt = this.getDeliveredAt();
        result = result * PRIME + ($deliveredAt == null ? 43 : $deliveredAt.hashCode());
        return result;
    }

    public String toString() {
        return "OrderDto(address=" + this.getAddress() + ", info=" + this.getInfo() + ", state=" + this.getState() + ", customer=" + this.getCustomer() + ", courierId=" + this.getCourierId() + ", ordered=" + this.getOrdered() + ", orderedAt=" + this.getOrderedAt() + ", deliveredAt=" + this.getDeliveredAt() + ")";
    }
}
