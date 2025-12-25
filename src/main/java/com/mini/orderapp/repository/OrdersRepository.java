package com.mini.orderapp.repository;

import com.mini.orderapp.domain.Orders;
import com.mini.orderapp.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Orders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> getOrdersByCustomerId(String customerId);

    Page<Orders> getOrdersByUser(User user, Pageable pageable);
}
