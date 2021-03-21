package com.shichko.delivery_service.model.entity;
import com.shichko.delivery_service.model.entity.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String address;
    private String info;
    @Enumerated
    private OrderState State;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<Ordered> ordered;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Courier courier;
}