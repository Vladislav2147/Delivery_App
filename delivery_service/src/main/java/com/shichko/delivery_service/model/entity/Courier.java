package com.shichko.delivery_service.model.entity;

import com.shichko.delivery_service.model.entity.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "courier")
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    private String phone;
    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private Collection<Order> orders;

}
