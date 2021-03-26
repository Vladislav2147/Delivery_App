package com.shichko.delivery_service.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Customer implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", nullable = false, length = 50)
    private String secondName;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;
    @Basic@Column(name = "phone", nullable = false, length = 50)
    private String phone;
    @OneToMany(mappedBy = "customerByCustomerId")
    private Collection<Order> ordersById;
}
