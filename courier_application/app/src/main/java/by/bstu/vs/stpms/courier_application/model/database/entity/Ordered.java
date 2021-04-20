package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    @Ignore
    private Product product;

    public Ordered() {
    }

    public long getId() {
        return this.id;
    }

    public int getAmount() {
        return this.amount;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public long getProductId() {
        return this.productId;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Ordered))
            return false;
        final Ordered other = (Ordered) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getAmount() != other.getAmount()) return false;
        if (this.getOrderId() != other.getOrderId()) return false;
        if (this.getProductId() != other.getProductId()) return false;
        final Object this$product = this.getProduct();
        final Object other$product = other.getProduct();
        if (this$product == null ? other$product != null : !this$product.equals(other$product))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Ordered;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        result = result * PRIME + this.getAmount();
        final long $orderId = this.getOrderId();
        result = result * PRIME + (int) ($orderId >>> 32 ^ $orderId);
        final long $productId = this.getProductId();
        result = result * PRIME + (int) ($productId >>> 32 ^ $productId);
        final Object $product = this.getProduct();
        result = result * PRIME + ($product == null ? 43 : $product.hashCode());
        return result;
    }

    public String toString() {
        return "Ordered(id=" + this.getId() + ", amount=" + this.getAmount() + ", orderId=" + this.getOrderId() + ", productId=" + this.getProductId() + ", product=" + this.getProduct() + ")";
    }
}
