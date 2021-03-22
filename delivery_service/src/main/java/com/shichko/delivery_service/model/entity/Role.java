package com.shichko.delivery_service.model.entity;

import com.shichko.delivery_service.model.entity.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Role {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "role", nullable = false)
    @Enumerated
    private RoleEnum role;
    @OneToMany(mappedBy = "roleByUserId")
    private Collection<UserRole> userRolesById;

}
