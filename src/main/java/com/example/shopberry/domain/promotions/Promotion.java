package com.example.shopberry.domain.promotions;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "promotion_name", nullable = false, unique = true, length = 100)
    private String promotionName;

    @Column(name = "discount_percent_value", nullable = false)
    private Long discountPercentValue;

}
