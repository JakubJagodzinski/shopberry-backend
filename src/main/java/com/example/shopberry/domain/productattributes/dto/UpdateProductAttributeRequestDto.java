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

}
