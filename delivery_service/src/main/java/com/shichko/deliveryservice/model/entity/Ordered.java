package com.shichko.deliveryservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Ordered extends AbstractEntity implements Serializable {
    @Basic@Column(name = "amount", nullable = false)
    private int amount;
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order orderByOrderId;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product productByProductId;

}
