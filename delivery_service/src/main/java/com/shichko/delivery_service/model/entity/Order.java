package com.shichko.delivery_service.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shichko.delivery_service.model.entity.enums.OrderState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "orders")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Order extends AbstractEntity implements Serializable {
    @Basic@Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic@Column(name = "info", nullable = true, length = 255)
    private String info;
    @Basic@Column(name = "state", nullable = false)
    @Enumerated
    private OrderState state;
    @ManyToOne@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customerByCustomerId;
    @ManyToOne@JoinColumn(name = "courier_id", referencedColumnName = "id")
    private User userByCourierId;
    @OneToMany(mappedBy = "orderByOrderId")
    private Collection<Ordered> orderedById;

}
