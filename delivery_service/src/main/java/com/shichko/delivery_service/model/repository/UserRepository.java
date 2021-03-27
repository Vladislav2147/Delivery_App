package com.shichko.delivery_service.model.repository;

import com.shichko.delivery_service.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<User> {
    User findByEmail(String email);
}
