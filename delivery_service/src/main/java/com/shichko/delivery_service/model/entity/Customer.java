package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Customer {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", nullable = false, length = 50)
    private String secondName;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;
    @Basic@Column(name = "phone", nullable = false, length = 50)
    private String phone;
    @OneToMany(mappedBy = "customer")
    private Collection<Order> ordersById;

}
