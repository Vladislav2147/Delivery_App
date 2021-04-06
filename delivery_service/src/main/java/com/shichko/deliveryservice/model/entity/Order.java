package com.shichko.deliveryservice.model.entity;

import com.shichko.deliveryservice.model.entity.enums.OrderState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "orders")
public class Order extends AbstractEntity implements Serializable {
    @Basic@Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic@Column(name = "info", nullable = true, length = 255)
    private String info;
    @Basic@Column(name = "state", nullable = false)
    @Enumerated
    private OrderState state;
    @ManyToOne@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @ManyToOne@JoinColumn(name = "courier_id", referencedColumnName = "id")
    private User courier;
    @OneToMany(mappedBy = "order")
    private Collection<Ordered> ordered;

}
