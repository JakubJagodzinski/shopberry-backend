package com.example.shopberry.domain.promotions.dto.request;

import com.example.shopberry.common.constants.messages.PromotionMessages;
import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionRequestDto {

    @Schema(
            description = "Unique name of the promotion",
            example = "Summer Sale",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("promotion_name")
    private String promotionName;

    @Schema(
            description = "Discount value in percent",
            example = "60",
            minimum = "0",
            maximum = "100",
            nullable = true
    )
    @Min(value = 0, message = PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE)
    @Max(value = 100, message = PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100)
    @JsonProperty("discount_percent_value")
    private Long discountPercentValue;

}
