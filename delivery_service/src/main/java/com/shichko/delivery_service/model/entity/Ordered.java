package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Ordered {
    @Basic@Column(name = "amount", nullable = false)
    private int amount;
    @Id@Column(name = "id", nullable = false)
    private long id;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

}
