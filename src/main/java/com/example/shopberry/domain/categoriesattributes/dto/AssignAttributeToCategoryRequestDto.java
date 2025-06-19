package com.example.shopberry.domain.categoriesattributes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignAttributeToCategoryRequestDto {

    @JsonProperty("attribute_id")
    private Long attributeId;

}
