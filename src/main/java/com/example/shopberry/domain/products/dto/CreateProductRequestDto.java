package com.example.shopberry.domain.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    private String productName;

    private Double productPrice;

    private Boolean isInStock;

    private byte[] image;

}
