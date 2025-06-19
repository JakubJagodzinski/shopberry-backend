package com.example.shopberry.domain.favouriteproducts.dto;

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
public class FavouriteProductResponseDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

}
