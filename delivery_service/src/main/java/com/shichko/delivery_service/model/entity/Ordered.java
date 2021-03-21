package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Ordered {
    @Basic@Column(name = "product_id", nullable = false)
    private long productId;
    @Basic@Column(name = "order_id", nullable = false)
    private long orderId;
    @Basic@Column(name = "amount", nullable = false)
    private int amount;
    @Id@Column(name = "id", nullable = false)
    private long id;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product productByProductId;
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order orderByOrderId;

}
