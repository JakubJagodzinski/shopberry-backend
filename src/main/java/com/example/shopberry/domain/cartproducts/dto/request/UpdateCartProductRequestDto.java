package com.example.shopberry.domain.cartproducts.dto.request;

import com.example.shopberry.common.constants.messages.ProductMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartProductRequestDto {

    @Schema(
            description = "Quantity of products to add. If null, quantity will not be updated",
            example = "1",
            minimum = "1",
            nullable = true
    )
    @Min(value = 1, message = ProductMessages.PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO)
    @JsonProperty("product_quantity")
    private Long productQuantity;

}
