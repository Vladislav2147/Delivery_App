package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Login {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "login", nullable = false, length = 64)
    private String login;
    @Basic@Column(name = "password", nullable = false, length = 64)
    private String password;
    @Basic@Column(name = "status", nullable = false)
    private int status;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;

}
