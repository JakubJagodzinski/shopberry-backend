package com.example.shopberry.domain.productattributes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductAttributeRequestDto {

    @Schema(
            description = "New value of the assigned attribute",
            examples = {"42.5", "3", "black", "true", "(5, 7)"},
            nullable = true
    )
    private String value;

    @Schema(
            description = "Weight defining how the attribute is important for this product",
            example = "0.7",
            minimum = "0",
            maximum = "1",
            nullable = true
    )
    private Double weight;

}
