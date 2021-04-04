package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CommonRepository<Role> {
    Role findFirstByName(String name);
}
