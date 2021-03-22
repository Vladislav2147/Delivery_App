package com.shichko.delivery_service.model.entity;

import com.shichko.delivery_service.model.entity.enums.OrderState;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Order {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
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
