package by.bstu.vs.stpms.courier_application.model.database.model.complex;

import androidx.room.Embedded;

import by.bstu.vs.stpms.courier_application.model.database.model.entity.Order;
import lombok.Data;

@Data
public class OrderAndCustomer {
    @Embedded
    private Order order;



}
