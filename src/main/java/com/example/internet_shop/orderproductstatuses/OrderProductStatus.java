package com.example.internet_shop.orderproductstatuses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_product_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_status_id")
    private Long orderProductStatusId;

    @Column(name = "status_name", nullable = false, unique = true, length = 100)
    private String statusName;

}
