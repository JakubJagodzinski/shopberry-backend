package com.example.shopberry.domain.orderproductstatuses;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_product_statuses")
public class OrderProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_status_id")
    private Long orderProductStatusId;

    @Column(name = "status_name", nullable = false, unique = true, length = 100)
    private String statusName;

    @Column(length = 500)
    private String description;

}
