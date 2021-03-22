package com.shichko.delivery_service.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role", schema = "dbo", catalog = "delivery_database")
public class UserRole {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Role roleByUserId;
    @ManyToOne@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private User userByRoleId;
}
