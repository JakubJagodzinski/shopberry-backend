package com.example.shopberry.domain.products.dto;

import com.example.shopberry.domain.categories.dto.CategoryResponseDto;
import com.example.shopberry.domain.producers.dto.ProducerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_price")
    private Double productPrice;

    @JsonProperty("discount_percent_value")
    private Double discountPercentValue;

    @JsonProperty("rating_value")
    private Double ratingValue;

    @JsonProperty("ratings_count")
    private Long ratingsCount;

    private ProducerResponseDto producer;

    private CategoryResponseDto category;

    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    private byte[] image;

}
