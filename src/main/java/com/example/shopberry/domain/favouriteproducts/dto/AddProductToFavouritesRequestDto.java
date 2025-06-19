package com.example.shopberry.domain.favouriteproducts.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToFavouritesRequestDto {

    @JsonProperty("product_id")
    private Long productId;

}
