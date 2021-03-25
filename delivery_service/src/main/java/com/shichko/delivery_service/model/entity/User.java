package com.shichko.delivery_service.model.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity(name = "usr")
public class User implements UserDetails {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
//    @Basic@Column(name = "first_name", length = 50)
//    private String firstName;
//    @Basic@Column(name = "second_name", length = 50)
//    private String secondName;
//    @Basic@Column(name = "phone", length = 20)
//    private String phone;
    @Basic@Column(name = "email", nullable = false, length = 320)
    private String email;
    @Basic@Column(name = "password", nullable = false, length = 64)
    private String password;
    @OneToMany(mappedBy = "userByCourierId")
    private Collection<Order> ordersById;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
//    @Basic
//    @Column(name = "is_approved")
//    private boolean isApproved;
    @Transient
    private String passwordConfirm;

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
