package com.example.internet_shop.orderproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    List<OrderProduct> findByIdOrderId(Long orderId);

    List<OrderProduct> findByIdProductId(Long productId);

    boolean existsById(OrderProductId id);

    boolean existsByOrderProductStatus_OrderProductStatusId(Long orderProductStatusId);

}
