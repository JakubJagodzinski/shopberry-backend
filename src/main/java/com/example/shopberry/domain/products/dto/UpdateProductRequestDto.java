package com.example.shopberry.domain.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequestDto {

    private String productName;

    private Double productPrice;

    private Double discountPercentValue;

    private Boolean isInStock;

    private byte[] image;

}
