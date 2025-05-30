package com.example.shopberry.domain.productpromotions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPromotionResponseDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("promotion_id")
    private Long promotionId;

}
