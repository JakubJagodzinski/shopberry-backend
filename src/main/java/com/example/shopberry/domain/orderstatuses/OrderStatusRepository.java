package com.example.shopberry.domain.orderstatuses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    boolean existsByOrderStatusName(String name);

    OrderStatus findByOrderStatusName(String name);

}
