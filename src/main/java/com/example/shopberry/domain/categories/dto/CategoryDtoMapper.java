package com.example.shopberry.domain.categories.dto;

import com.example.shopberry.domain.categories.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDtoMapper {

    public CategoryResponseDto toDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.setCategoryId(category.getCategoryId());
        categoryResponseDto.setCategoryName(category.getCategoryName());
        if (category.getParentCategory() != null) {
            categoryResponseDto.setParentCategoryId(category.getParentCategory().getCategoryId());
        } else {
            categoryResponseDto.setParentCategoryId(null);
        }

        return categoryResponseDto;
    }

    public List<CategoryResponseDto> toDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }

}
