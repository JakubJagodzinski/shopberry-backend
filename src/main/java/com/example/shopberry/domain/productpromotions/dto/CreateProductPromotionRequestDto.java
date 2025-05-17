package com.example.shopberry.domain.productpromotions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductPromotionRequestDto {

    private Long productId;

    private Long promotionId;

}
