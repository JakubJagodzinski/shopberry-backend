package com.example.shopberry.domain.favouriteentries.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToCustomerFavouritesRequestDto {

    @JsonProperty("product_id")
    private Long productId;

}
