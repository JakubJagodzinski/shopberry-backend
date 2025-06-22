package com.example.shopberry.domain.productattributes.dto;

import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeResponseDto {

    private ProductResponseDto product;

    private AttributeResponseDto attribute;

    private String value;

    private Double weight;

}
