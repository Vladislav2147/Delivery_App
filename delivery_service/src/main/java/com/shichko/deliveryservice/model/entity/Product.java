package com.shichko.deliveryservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends AbstractEntity implements Serializable {
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic@Column(name = "weight", nullable = false, precision = 0)
    private double weight;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<Ordered> orderedById;

}
