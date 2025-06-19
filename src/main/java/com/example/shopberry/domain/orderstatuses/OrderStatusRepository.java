package com.example.shopberry.domain.orderstatuses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    boolean existsByOrderStatusName(String orderStatusName);

    Optional<OrderStatus> findByOrderStatusName(String orderStatusName);

}
