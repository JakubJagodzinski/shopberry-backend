package com.example.shopberry.domain.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_price")
    private Double productPrice;

    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    private byte[] image;

}
