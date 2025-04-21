package com.example.internet_shop.categories;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDtoMapper {

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        if (category.getParentCategory() != null) {
            categoryDto.setParentCategoryId(category.getParentCategory().getCategoryId());
        } else {
            categoryDto.setParentCategoryId(null);
        }

        return categoryDto;
    }

    public List<CategoryDto> toDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }

}
