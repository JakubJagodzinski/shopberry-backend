package com.example.internet_shop.categoriesattributes.dto;


import com.example.internet_shop.categoriesattributes.CategoryAttribute;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryAttributeDtoMapper {

    public CategoryAttributeResponseDto toDto(CategoryAttribute categoryAttribute) {
        CategoryAttributeResponseDto categoryAttributeResponseDto = new CategoryAttributeResponseDto();

        categoryAttributeResponseDto.setCategoryId(categoryAttribute.getCategory().getCategoryId());
        categoryAttributeResponseDto.setAttributeId(categoryAttribute.getAttribute().getAttributeId());

        return categoryAttributeResponseDto;
    }

    public List<CategoryAttributeResponseDto> toDtoList(List<CategoryAttribute> categoryAttributes) {
        return categoryAttributes.stream()
                .map(this::toDto)
                .toList();
    }

}
