package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<User> {
    User findByEmail(String email);
}
