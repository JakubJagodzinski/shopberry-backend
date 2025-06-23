package com.example.shopberry.domain.categoriesattributes.dto.response;

import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import com.example.shopberry.domain.categories.dto.response.CategoryResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"category", "attribute"})
public class CategoryAttributeResponseDto {

    private CategoryResponseDto category;

    private AttributeResponseDto attribute;

}
