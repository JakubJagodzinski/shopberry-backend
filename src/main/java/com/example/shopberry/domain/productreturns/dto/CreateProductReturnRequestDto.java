package com.example.shopberry.domain.productreturns.dto;

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
public class CreateProductReturnRequestDto {

    @Schema(
            description = "ID of the product being returned",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    @Schema(
            description = "ID representing the cause for the product return",
            example = "5"
    )
    @NotNull
    @JsonProperty("cause_of_return_id")
    private Long causeOfReturnId;

}
