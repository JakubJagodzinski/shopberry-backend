package com.example.shopberry.domain.products.dto;

import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.common.constants.messages.PromotionMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    @Schema(
            description = "Unique name of the product",
            example = "TV QLed 4k FullHD"
    )
    @NotBlank
    @JsonProperty("product_name")
    private String productName;

    @Schema(
            description = "Product unit price",
            example = "19.99",
            minimum = "0.0"
    )
    @NotNull
    @Min(value = 0, message = ProductMessages.PRODUCT_PRICE_CANNOT_BE_NEGATIVE)
    @JsonProperty("product_price")
    private Double productPrice;

    @Schema(
            description = "Product constant discount value in percent (not from promotion)",
            example = "60",
            minimum = "0",
            maximum = "100",
            defaultValue = "0",
            nullable = true
    )
    @Min(value = 0, message = PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE)
    @Max(value = 100, message = PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100)
    @JsonProperty("discount_percent_value")
    private Double discountPercentValue;

    @Schema(
            description = "Id of product producer",
            example = "1"
    )
    @NotNull
    private Long producerId;

    @Schema(
            description = "Id of product category. Category cannot be parent to any category",
            example = "1"
    )
    @NotNull
    private Long categoryId;

    @Schema(
            description = "Indicates if product is available",
            example = "false",
            defaultValue = "true",
            nullable = true
    )
    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    @Schema(
            description = "Base64 representation of product image",
            example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=",
            defaultValue = "null",
            nullable = true
    )
    private byte[] image;

}
