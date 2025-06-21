package com.example.shopberry.domain.orderproducts.dto;

import com.example.shopberry.common.constants.messages.ProductMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToOrderRequestDto {

    @Schema(
            description = "Id of product to add to order. Product can be added once to each order",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    @Schema(
            description = "Quantity of products to add to order",
            example = "1",
            minimum = "1"
    )
    @NotNull
    @Min(value = 1, message = ProductMessages.PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO)
    @JsonProperty("product_quantity")
    private Long productQuantity;

    @Schema(
            description = "Product unit price",
            example = "19.99",
            minimum = "0.0"
    )
    @NotNull
    @Min(value = 0, message = ProductMessages.PRODUCT_PRICE_CANNOT_BE_NEGATIVE)
    @JsonProperty("product_price")
    private Double productPrice;

}
