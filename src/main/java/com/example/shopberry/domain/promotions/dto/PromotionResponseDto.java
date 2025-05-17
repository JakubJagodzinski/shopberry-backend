package com.example.shopberry.domain.promotions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponseDto {

    private Long promotionId;

    private String promotionName;

    private Long discountPercentValue;

}
