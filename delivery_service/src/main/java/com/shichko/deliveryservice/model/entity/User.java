package com.shichko.deliveryservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "usr")
@Transactional
public class User extends AbstractEntity implements UserDetails, Serializable {
    @Basic@Column(name = "first_name", length = 50)
    private String firstName;
    @Basic@Column(name = "second_name", length = 50)
    private String secondName;
    @Basic@Column(name = "phone", length = 20)
    private String phone;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;
    @Basic@Column(name = "password", nullable = false, length = 64)
    private String password;
    @OneToMany(mappedBy = "courier", fetch = FetchType.EAGER)
    private Collection<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
