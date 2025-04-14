package com.example.internet_shop.orderproductstatuses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductStatusRepository extends JpaRepository<OrderProductStatus, Long> {
}
