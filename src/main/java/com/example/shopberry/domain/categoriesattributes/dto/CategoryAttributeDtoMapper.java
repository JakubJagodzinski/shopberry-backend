package com.example.shopberry.domain.categoriesattributes.dto;


import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.categories.dto.CategoryDtoMapper;
import com.example.shopberry.domain.categoriesattributes.CategoryAttribute;
import com.example.shopberry.domain.categoriesattributes.dto.response.CategoryAttributeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryAttributeDtoMapper {

    private final CategoryDtoMapper categoryDtoMapper;
    private final AttributeDtoMapper attributeDtoMapper;

    public CategoryAttributeResponseDto toDto(CategoryAttribute categoryAttribute) {
        if (categoryAttribute == null) {
            return null;
        }

        CategoryAttributeResponseDto categoryAttributeResponseDto = new CategoryAttributeResponseDto();

        categoryAttributeResponseDto.setCategory(categoryDtoMapper.toDto(categoryAttribute.getCategory()));
        categoryAttributeResponseDto.setAttribute(attributeDtoMapper.toDto(categoryAttribute.getAttribute()));

        return categoryAttributeResponseDto;
    }

    public List<CategoryAttributeResponseDto> toDtoList(List<CategoryAttribute> categoryAttributeList) {
        return categoryAttributeList.stream()
                .map(this::toDto)
                .toList();
    }

}
