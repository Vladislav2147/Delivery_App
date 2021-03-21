package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Order {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "customer_id", nullable = false)
    private long customerId;
    @Basic@Column(name = "courier_id", nullable = true)
    private Long courierId;
    @Basic@Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic@Column(name = "info", nullable = true, length = 255)
    private String info;
    @Basic@Column(name = "state", nullable = false)
    private int state;
    @ManyToOne@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customerByCustomerId;
    @ManyToOne@JoinColumn(name = "courier_id", referencedColumnName = "id")
    private Courier courierByCourierId;
    @OneToMany(mappedBy = "orderByOrderId")
    private Collection<Ordered> orderedsById;

}
