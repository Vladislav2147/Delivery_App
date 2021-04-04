package com.shichko.deliveryservice.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
}
