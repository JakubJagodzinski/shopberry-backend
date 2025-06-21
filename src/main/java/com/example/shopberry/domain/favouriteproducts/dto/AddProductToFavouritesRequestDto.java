package com.example.shopberry.domain.favouriteproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToFavouritesRequestDto {

    @Schema(
            description = "Id of product to add to favourites",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

}
