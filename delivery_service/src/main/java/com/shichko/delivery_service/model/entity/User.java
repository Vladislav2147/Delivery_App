package com.shichko.delivery_service.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", nullable = false, length = 50)
    private String secondName;
    @Basic@Column(name = "phone", nullable = false, length = 20)
    private String phone;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;
    @Basic@Column(name = "password", nullable = false, length = 64)
    private String password;
    @Basic@Column(name = "password_salt", nullable = false, length = 64)
    private String passwordSalt;
    @OneToMany(mappedBy = "userByCourierId")
    private Collection<Order> ordersById;
    @OneToMany(mappedBy = "userByRoleId")
    private Collection<UserRole> userRolesById;

}
