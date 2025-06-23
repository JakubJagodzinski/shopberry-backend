package com.example.shopberry.domain.categories.dto;

import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.dto.response.CategoryResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDtoMapper {

    public CategoryResponseDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.setCategoryId(category.getCategoryId());
        categoryResponseDto.setCategoryName(category.getCategoryName());
        categoryResponseDto.setParentCategory(toDto(category.getParentCategory()));

        return categoryResponseDto;
    }

    public List<CategoryResponseDto> toDtoList(List<Category> categoryList) {
        return categoryList.stream()
                .map(this::toDto)
                .toList();
    }

}
