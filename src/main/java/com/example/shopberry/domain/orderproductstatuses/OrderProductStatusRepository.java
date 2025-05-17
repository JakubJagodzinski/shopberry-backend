package com.example.shopberry.domain.orderproductstatuses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderProductStatusRepository extends JpaRepository<OrderProductStatus, Long> {

    boolean existsByStatusName(String statusName);

    Optional<OrderProductStatus> findByStatusName(String statusName);

}
