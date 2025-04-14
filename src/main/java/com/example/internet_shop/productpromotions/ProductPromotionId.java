package com.example.internet_shop.productpromotions;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductPromotionId {

    private Long productId;

    private Long promotionId;

}
