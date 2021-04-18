package by.bstu.vs.stpms.courier_application.model.network.dto;

import lombok.Data;

@Data
public class OrderedDto extends AbstractDto {
    private int amount;
    private long orderId;
    private ProductDto product;
}
