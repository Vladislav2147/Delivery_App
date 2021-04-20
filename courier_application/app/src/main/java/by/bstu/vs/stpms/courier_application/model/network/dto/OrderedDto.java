package by.bstu.vs.stpms.courier_application.model.network.dto;

public class OrderedDto extends AbstractDto {
    private int amount;
    private long orderId;
    private ProductDto product;

    public OrderedDto() {
    }

    public int getAmount() {
        return this.amount;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public ProductDto getProduct() {
        return this.product;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderedDto))
            return false;
        final OrderedDto other = (OrderedDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getAmount() != other.getAmount()) return false;
        if (this.getOrderId() != other.getOrderId()) return false;
        final Object this$product = this.getProduct();
        final Object other$product = other.getProduct();
        if (this$product == null ? other$product != null : !this$product.equals(other$product))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderedDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getAmount();
        final long $orderId = this.getOrderId();
        result = result * PRIME + (int) ($orderId >>> 32 ^ $orderId);
        final Object $product = this.getProduct();
        result = result * PRIME + ($product == null ? 43 : $product.hashCode());
        return result;
    }

    public String toString() {
        return "OrderedDto(amount=" + this.getAmount() + ", orderId=" + this.getOrderId() + ", product=" + this.getProduct() + ")";
    }
}
