package com.example.internet_shop.promotions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionDto {

    private String promotionName;

    private Long discountPercentValue;

}
