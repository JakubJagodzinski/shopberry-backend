package com.example.shopberry.domain.productattributes.dto;

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
public class AssignAttributeToProductRequestDto {

    @Schema(
            description = "ID of the attribute to be assigned to the product",
            example = "1"
    )
    @NotNull
    @JsonProperty("attribute_id")
    private Long attributeId;

    @Schema(
            description = "Value of the assigned attribute",
            examples = {"42.5", "3", "black", "true", "(5, 7)"}
    )
    @NotNull
    private String value;

    @Schema(
            description = "Weight defining how the attribute is important for this product",
            example = "0.7",
            minimum = "0",
            maximum = "1"
    )
    @NotNull
    private Double weight;

}
