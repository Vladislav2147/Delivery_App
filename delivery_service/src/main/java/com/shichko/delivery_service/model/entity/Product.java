package com.shichko.delivery_service.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Product {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;
    @Basic@Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic@Column(name = "weight", nullable = false, precision = 0)
    private double weight;
    @OneToMany(mappedBy = "product")
    private Collection<Ordered> ordered;

}
