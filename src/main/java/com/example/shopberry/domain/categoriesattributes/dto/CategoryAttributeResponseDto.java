package com.example.shopberry.domain.categoriesattributes.dto;

import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import com.example.shopberry.domain.categories.dto.CategoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAttributeResponseDto {

    private CategoryResponseDto category;

    private AttributeResponseDto attribute;

}
