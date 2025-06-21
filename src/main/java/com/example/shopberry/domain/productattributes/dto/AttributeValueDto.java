package com.example.shopberry.domain.productattributes.dto;

import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueDto {

    private AttributeResponseDto attribute;

    private Double value;

}
