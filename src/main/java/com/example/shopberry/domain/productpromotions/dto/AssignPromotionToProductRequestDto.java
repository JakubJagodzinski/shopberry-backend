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
public class AssignPromotionToProductRequestDto {

    @JsonProperty("promotion_id")
    private Long promotionId;

}
