package com.example.shopberry.orderproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    List<OrderProduct> findByOrder_OrderId(Long orderId);

    boolean existsByOrderProductStatus_OrderProductStatusId(Long orderProductStatusId);

}
