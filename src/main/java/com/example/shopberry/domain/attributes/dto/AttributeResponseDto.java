package com.example.shopberry.domain.attributes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponseDto {

    @JsonProperty("attribute_id")
    private Long attributeId;

    @JsonProperty("attribute_name")
    private String attributeName;

}
