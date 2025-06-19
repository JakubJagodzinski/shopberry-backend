package com.example.shopberry.domain.orderproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToOrderRequestDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_quantity")
    private Long productQuantity;

    @JsonProperty("product_price")
    private Double productPrice;

}
