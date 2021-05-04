package by.bstu.vs.stpms.courier_application.model.database.entity.enums;

public enum OrderState {
    Ordered,
    Delivering,
    Delivered,
    Canceled;

    public OrderState getNext() {
       OrderState[] states = OrderState.values();
       int nextIndex = this.ordinal() + 1;
       if (nextIndex == states.length) {
           return states[0];
       } else {
           return states[nextIndex];
       }
    }
}
