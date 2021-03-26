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
public class Product implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic@Column(name = "weight", nullable = false, precision = 0)
    private double weight;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<Ordered> orderedById;

}
