package com.example.shopberry.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer_UserId(UUID customerId);

}
