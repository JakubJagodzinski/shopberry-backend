package com.example.internet_shop.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;

    private Double productPrice;

    private Double discountPercentValue;

    private Double ratingValue;

    private Long ratingsCount;

    private Boolean isInStock;

    private byte[] image;

}
