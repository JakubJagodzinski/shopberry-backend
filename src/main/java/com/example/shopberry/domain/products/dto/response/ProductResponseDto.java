package com.example.shopberry.domain.products.dto.response;

import com.example.shopberry.domain.producers.dto.response.ProducerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"product_id", "product_name", "product_price", "discount_percent_value", "rating_value", "ratings_count", "producer", "category_id", "is_in_stock", "image"})
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

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    private String image;

}
