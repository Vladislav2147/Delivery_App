package com.shichko.delivery_service.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Ordered implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "amount", nullable = false)
    private int amount;
    @ManyToOne@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order orderByOrderId;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product productByProductId;

}
