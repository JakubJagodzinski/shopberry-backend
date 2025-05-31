package com.example.shopberry.domain.orderstatuses;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_statuses")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @Column(name = "order_status_name", nullable = false, unique = true, length = 100)
    private String orderStatusName;

}
