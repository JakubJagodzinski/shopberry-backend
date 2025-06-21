package com.example.shopberry.domain.productpromotions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignPromotionToProductRequestDto {

    @Schema(
            description = "ID of the promotion to assign to the product",
            example = "1"
    )
    @NotNull
    @JsonProperty("promotion_id")
    private Long promotionId;

}
