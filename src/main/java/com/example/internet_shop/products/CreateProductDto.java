package com.example.internet_shop.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    private String productName;

    private Double productPrice;

    private Boolean isInStock;

    private byte[] image;

}
