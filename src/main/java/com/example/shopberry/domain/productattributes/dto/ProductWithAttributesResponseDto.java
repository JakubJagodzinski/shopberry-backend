package com.example.shopberry.domain.productattributes.dto;

import com.example.shopberry.domain.products.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithAttributesResponseDto {

    private ProductResponseDto product;

    private List<AttributeValueDto> attributes;

}
