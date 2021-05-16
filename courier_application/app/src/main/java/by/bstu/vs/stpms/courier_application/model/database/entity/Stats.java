package by.bstu.vs.stpms.courier_application.model.database.entity;

public class Stats {
    private long courierId;
    private int deliveredOrdersCount;
    private int deliveredInTimeCount;
    private double deliveredTotalPrice;
    private int deliveredProductsCount;

    public Stats() {
    }

    public Stats(long courierId, int deliveredOrdersCount, int deliveredInTimeCount, double deliveredTotalPrice, int deliveredProductsCount) {
        this.courierId = courierId;
        this.deliveredOrdersCount = deliveredOrdersCount;
        this.deliveredInTimeCount = deliveredInTimeCount;
        this.deliveredTotalPrice = deliveredTotalPrice;
        this.deliveredProductsCount = deliveredProductsCount;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public int getDeliveredOrdersCount() {
        return deliveredOrdersCount;
    }

    public void setDeliveredOrdersCount(int deliveredOrdersCount) {
        this.deliveredOrdersCount = deliveredOrdersCount;
    }

    public int getDeliveredInTimeCount() {
        return deliveredInTimeCount;
    }

    public void setDeliveredInTimeCount(int deliveredInTimeCount) {
        this.deliveredInTimeCount = deliveredInTimeCount;
    }

    public double getDeliveredTotalPrice() {
        return deliveredTotalPrice;
    }

    public void setDeliveredTotalPrice(double deliveredTotalPrice) {
        this.deliveredTotalPrice = deliveredTotalPrice;
    }

    public int getDeliveredProductsCount() {
        return deliveredProductsCount;
    }

    public void setDeliveredProductsCount(int deliveredProductsCount) {
        this.deliveredProductsCount = deliveredProductsCount;
    }
}