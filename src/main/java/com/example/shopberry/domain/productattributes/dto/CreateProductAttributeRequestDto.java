package com.example.shopberry.domain.productattributes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductAttributeRequestDto {

    private Long productId;

    private Long attributeId;

    private Double value;

}
