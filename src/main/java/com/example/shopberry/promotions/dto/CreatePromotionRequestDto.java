package com.example.shopberry.promotions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionRequestDto {

    private String promotionName;

    private Long discountPercentValue;

}
