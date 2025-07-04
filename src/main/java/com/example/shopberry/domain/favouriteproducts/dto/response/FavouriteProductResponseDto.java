package com.example.shopberry.domain.favouriteproducts.dto.response;

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
@JsonPropertyOrder({"customer_id", "product", "added_at"})
public class FavouriteProductResponseDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    private ProductResponseDto product;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

}
