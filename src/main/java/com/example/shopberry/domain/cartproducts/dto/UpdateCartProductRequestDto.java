package com.example.shopberry.domain.cartproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartProductRequestDto {

    @JsonProperty("product_quantity")
    private Long productQuantity;

}
