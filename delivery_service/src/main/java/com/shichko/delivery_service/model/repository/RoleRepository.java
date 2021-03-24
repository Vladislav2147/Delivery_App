package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
