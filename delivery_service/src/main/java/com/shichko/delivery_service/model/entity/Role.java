package com.shichko.delivery_service.model.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "role", nullable = false)
    private String role;
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Collection<User> users;

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role() {

    }

    @Override
    public String getAuthority() {
        return role;
    }
}
