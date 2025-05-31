package com.example.shopberry.domain.productpromotions;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductPromotionId {

    private Long productId;

    private Long promotionId;

}
