package com.example.shopberry.domain.orderproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    List<OrderProduct> findAllByOrder_OrderId(Long orderId);

    boolean existsByOrderProductStatus_OrderProductStatusId(Long orderProductStatusId);

}
