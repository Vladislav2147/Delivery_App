package com.shichko.deliveryservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Role extends AbstractEntity implements GrantedAuthority, Serializable {
    @Basic@Column(name = "name", nullable = false)
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    public Role(long id, String name) {
        setId(id);
        this.name = name;
    }

    public Role() {

    }

    @Override
    public String getAuthority() {
        return name;
    }
}
