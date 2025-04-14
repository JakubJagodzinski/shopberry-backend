package com.example.internet_shop.orderproducts;

import jakarta.persistence.Column;

public class OrderProductId {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

}
