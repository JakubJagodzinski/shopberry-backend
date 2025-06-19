package com.example.shopberry.domain.favouriteproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteProductResponseDto {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

}
