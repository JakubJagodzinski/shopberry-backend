package com.example.shopberry.domain.productattributes.dto.response;

import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"attribute", "value", "weight"})
public class AttributeValueResponseDto {

    private AttributeResponseDto attribute;

    private String value;

    private Double weight;

}
