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
            example = "42.5",
            nullable = true
    )
    private Double value;

}
