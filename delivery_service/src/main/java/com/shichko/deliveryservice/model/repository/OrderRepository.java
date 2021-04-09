package com.shichko.deliveryservice.model.repository;

import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.enums.OrderState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CommonRepository<Order> {
    @Modifying
    @Query("update orders o set o.state = :newState where o.id = :id")
    void updateStateById(@Param("id") long id, @Param("newState") OrderState newState);
}
