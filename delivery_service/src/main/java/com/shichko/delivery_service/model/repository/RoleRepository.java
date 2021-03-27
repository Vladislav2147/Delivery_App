package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CommonRepository<Role> {
    Role findFirstByName(String name);
}
