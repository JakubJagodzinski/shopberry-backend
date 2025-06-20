package com.example.shopberry.domain.cartproducts.dto;

import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CartProductResponseDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    private ProductResponseDto product;

    private Long quantity;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

}
