package com.example.shopberry.domain.productpromotions;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPromotionId {

    private Long productId;

    private Long promotionId;

}
