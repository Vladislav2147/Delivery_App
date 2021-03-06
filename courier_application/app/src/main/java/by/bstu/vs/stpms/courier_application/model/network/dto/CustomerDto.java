package by.bstu.vs.stpms.courier_application.model.network.dto;

import java.util.Collection;

public class CustomerDto extends AbstractDto {
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private Collection<Long> ordersId;

    public CustomerDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Collection<Long> getOrdersId() {
        return this.ordersId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrdersId(Collection<Long> ordersId) {
        this.ordersId = ordersId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CustomerDto))
            return false;
        final CustomerDto other = (CustomerDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName))
            return false;
        final Object this$secondName = this.getSecondName();
        final Object other$secondName = other.getSecondName();
        if (this$secondName == null ? other$secondName != null : !this$secondName.equals(other$secondName))
            return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email))
            return false;
        final Object this$phone = this.getPhone();
        final Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone))
            return false;
        final Object this$ordersId = this.getOrdersId();
        final Object other$ordersId = other.getOrdersId();
        if (this$ordersId == null ? other$ordersId != null : !this$ordersId.equals(other$ordersId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CustomerDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $secondName = this.getSecondName();
        result = result * PRIME + ($secondName == null ? 43 : $secondName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final Object $ordersId = this.getOrdersId();
        result = result * PRIME + ($ordersId == null ? 43 : $ordersId.hashCode());
        return result;
    }

    public String toString() {
        return "CustomerDto(firstName=" + this.getFirstName() + ", secondName=" + this.getSecondName() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", ordersId=" + this.getOrdersId() + ")";
    }
}
