package com.example.shopberry.domain.productpromotions.dto;

import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.promotions.dto.PromotionResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"product", "promotion"})
public class ProductPromotionResponseDto {

    private ProductResponseDto product;

    private PromotionResponseDto promotion;

}
