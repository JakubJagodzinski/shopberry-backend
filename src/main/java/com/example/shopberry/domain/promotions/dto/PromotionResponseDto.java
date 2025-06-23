package com.example.shopberry.domain.promotions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"promotion_id", "promotion_name", "discount_percent_value"})
public class PromotionResponseDto {

    @JsonProperty("promotion_id")
    private Long promotionId;

    @JsonProperty("promotion_name")
    private String promotionName;

    @JsonProperty("discount_percent_value")
    private Long discountPercentValue;

}
