package com.example.shopberry.domain.categories;

import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.example.shopberry.domain.categories.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;

    public List<CategoryResponseDto> getAllCategories() {
        return categoryDtoMapper.toDtoList(categoryRepository.findAll());
    }

    @Transactional
    public CategoryResponseDto getCategoryById(Long id) throws EntityNotFoundException {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        return categoryDtoMapper.toDto(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) throws IllegalArgumentException {
        if (categoryRepository.existsByCategoryName(createCategoryRequestDto.getCategoryName())) {
            throw new IllegalArgumentException(CategoryMessages.CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        Category category = new Category();

        category.setCategoryName(createCategoryRequestDto.getCategoryName());

        Long parentCategoryId = createCategoryRequestDto.getParentCategoryId();

        if (parentCategoryId != null) {
            Category parentCategory = categoryRepository.findById(parentCategoryId).orElse(null);

            if (parentCategory == null) {
                throw new IllegalArgumentException(CategoryMessages.PARENT_CATEGORY_NOT_FOUND);
            }

            category.setParentCategory(parentCategory);
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponseDto setParentCategory(Long categoryId, SetParentCategoryRequestDto setParentCategoryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (categoryId.equals(setParentCategoryRequestDto.getParentCategoryId())) {
            throw new IllegalArgumentException(CategoryMessages.CATEGORY_CANNOT_BE_PARENT_TO_ITSELF);
        }

        Category childCategory = categoryRepository.findById(categoryId).orElse(null);

        if (childCategory == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        Category parentCategory = categoryRepository.findById(setParentCategoryRequestDto.getParentCategoryId()).orElse(null);

        if (parentCategory == null) {
            throw new IllegalArgumentException(CategoryMessages.PARENT_CATEGORY_NOT_FOUND);
        }

        childCategory.setParentCategory(parentCategory);

        return categoryDtoMapper.toDto(categoryRepository.save(childCategory));
    }

    @Transactional
    public CategoryResponseDto updateCategoryById(Long id, UpdateCategoryRequestDto updateCategoryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        if (updateCategoryRequestDto.getCategoryName() != null) {
            Category otherCategory = categoryRepository.findByCategoryName(updateCategoryRequestDto.getCategoryName()).orElse(null);

            if (otherCategory != null && !otherCategory.getCategoryId().equals(id)) {
                throw new IllegalArgumentException(CategoryMessages.CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            category.setCategoryName(updateCategoryRequestDto.getCategoryName());
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long id) throws EntityNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        List<Category> childCategories = categoryRepository.findAllByParentCategory_CategoryId(id);

        for (Category childCategory : childCategories) {
            childCategory.setParentCategory(null);
        }

        categoryRepository.deleteById(id);
    }

}
