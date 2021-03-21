package com.shichko.delivery_service.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Admin {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", nullable = false, length = 50)
    private String secondName;
    @OneToOne@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private Login loginById;

}
