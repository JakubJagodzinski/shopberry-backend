package com.example.shopberry.domain.categories;

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

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    private static final String PARENT_CATEGORY_NOT_FOUND_MESSAGE = "Parent category not found";
    private static final String CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Category with that name already exists";
    private static final String CATEGORY_CANNOT_BE_PARENT_TO_ITSELF_MESSAGE = "Category cannot be parent to itself";

    public List<CategoryResponseDto> getAllCategories() {
        return categoryDtoMapper.toDtoList(categoryRepository.findAll());
    }

    @Transactional
    public CategoryResponseDto getCategoryById(Long id) throws EntityNotFoundException {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        return categoryDtoMapper.toDto(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) throws IllegalArgumentException {
        if (categoryRepository.existsByCategoryName(createCategoryRequestDto.getCategoryName())) {
            throw new IllegalArgumentException(CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        Category category = new Category();

        category.setCategoryName(createCategoryRequestDto.getCategoryName());

        Long parentCategoryId = createCategoryRequestDto.getParentCategoryId();

        if (parentCategoryId != null) {
            Category parentCategory = categoryRepository.findById(parentCategoryId).orElse(null);

            if (parentCategory == null) {
                throw new IllegalArgumentException(PARENT_CATEGORY_NOT_FOUND_MESSAGE);
            }

            category.setParentCategory(parentCategory);
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponseDto setParentCategory(Long categoryId, SetParentCategoryRequestDto setParentCategoryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (categoryId.equals(setParentCategoryRequestDto.getParentCategoryId())) {
            throw new IllegalArgumentException(CATEGORY_CANNOT_BE_PARENT_TO_ITSELF_MESSAGE);
        }

        Category childCategory = categoryRepository.findById(categoryId).orElse(null);

        if (childCategory == null) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        Category parentCategory = categoryRepository.findById(setParentCategoryRequestDto.getParentCategoryId()).orElse(null);

        if (parentCategory == null) {
            throw new IllegalArgumentException(PARENT_CATEGORY_NOT_FOUND_MESSAGE);
        }

        childCategory.setParentCategory(parentCategory);

        return categoryDtoMapper.toDto(categoryRepository.save(childCategory));
    }

    @Transactional
    public CategoryResponseDto updateCategoryById(Long id, UpdateCategoryRequestDto updateCategoryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        if (updateCategoryRequestDto.getCategoryName() != null) {
            Category otherCategory = categoryRepository.findByCategoryName(updateCategoryRequestDto.getCategoryName());

            if (otherCategory != null && !otherCategory.getCategoryId().equals(id)) {
                throw new IllegalArgumentException(CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            category.setCategoryName(updateCategoryRequestDto.getCategoryName());
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long id) throws EntityNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        List<Category> childCategories = categoryRepository.findAllByParentCategory_CategoryId(id);

        for (Category childCategory : childCategories) {
            childCategory.setParentCategory(null);
        }

        categoryRepository.deleteById(id);
    }

}
