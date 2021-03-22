package com.shichko.delivery_service.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Ordered {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "amount", nullable = false)
    private int amount;
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order orderByOrderId;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product productByProductId;

}
