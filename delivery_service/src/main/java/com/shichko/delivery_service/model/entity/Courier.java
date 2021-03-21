package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Courier {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", nullable = false, length = 50)
    private String secondName;
    @Basic@Column(name = "phone", nullable = false, length = 20)
    private String phone;
    @OneToOne@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private Login loginById;
    @OneToMany(mappedBy = "courier")
    private Collection<Order> ordersById;

}
