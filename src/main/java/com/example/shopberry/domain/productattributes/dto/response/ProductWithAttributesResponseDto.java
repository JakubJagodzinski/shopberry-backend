package com.example.shopberry.domain.productattributes.dto.response;

import com.example.shopberry.domain.products.dto.response.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"product", "attributes"})
public class ProductWithAttributesResponseDto {

    private ProductResponseDto product;

    private List<AttributeValueResponseDto> attributes;

}
