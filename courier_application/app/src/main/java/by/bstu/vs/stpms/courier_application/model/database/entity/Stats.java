package by.bstu.vs.stpms.courier_application.model.database.entity;

public class Stats {
    private long courierId;
    private int deliveredOrdersCount;
    private int deliveredInTimeCount;

    public Stats() {
    }

    public Stats(long courierId, int deliveredOrdersCount, int deliveredInTimeCount) {
        this.courierId = courierId;
        this.deliveredOrdersCount = deliveredOrdersCount;
        this.deliveredInTimeCount = deliveredInTimeCount;
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
}