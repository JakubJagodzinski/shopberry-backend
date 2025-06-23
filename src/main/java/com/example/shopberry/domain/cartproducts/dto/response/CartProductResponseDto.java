package com.example.shopberry.domain.cartproducts.dto.response;

import com.example.shopberry.domain.products.dto.response.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customer_id", "product", "product_quantity", "added_at"})
public class CartProductResponseDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    private ProductResponseDto product;

    @JsonProperty("product_quantity")
    private Long productQuantity;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

}
