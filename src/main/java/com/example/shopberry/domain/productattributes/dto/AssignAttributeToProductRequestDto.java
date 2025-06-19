package com.example.shopberry.domain.productattributes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignAttributeToProductRequestDto {

    @JsonProperty("attribute_id")
    private Long attributeId;

    private Double value;

}
