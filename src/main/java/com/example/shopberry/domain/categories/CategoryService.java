package com.example.shopberry.domain.categories;

import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.example.shopberry.domain.categories.dto.*;
import jakarta.persistence.EntityExistsException;
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
    public CategoryResponseDto getCategoryById(Long categoryId) throws EntityNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        return categoryDtoMapper.toDto(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) throws EntityNotFoundException, EntityExistsException {
        String categoryName = createCategoryRequestDto.getCategoryName().trim();

        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new EntityExistsException(CategoryMessages.CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        Category category = new Category();

        category.setCategoryName(categoryName);

        Long parentCategoryId = createCategoryRequestDto.getParentCategoryId();

        if (parentCategoryId != null) {
            Category parentCategory = categoryRepository.findById(parentCategoryId).orElse(null);

            if (parentCategory == null) {
                throw new EntityNotFoundException(CategoryMessages.PARENT_CATEGORY_NOT_FOUND);
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
            throw new EntityNotFoundException(CategoryMessages.PARENT_CATEGORY_NOT_FOUND);
        }

        childCategory.setParentCategory(parentCategory);

        return categoryDtoMapper.toDto(categoryRepository.save(childCategory));
    }

    @Transactional
    public CategoryResponseDto updateCategoryById(Long categoryId, UpdateCategoryRequestDto updateCategoryRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        if (updateCategoryRequestDto.getCategoryName() != null) {
            String categoryName = updateCategoryRequestDto.getCategoryName().trim();

            Category otherCategory = categoryRepository.findByCategoryName(categoryName).orElse(null);

            if (otherCategory != null && !otherCategory.getCategoryId().equals(categoryId)) {
                throw new EntityExistsException(CategoryMessages.CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            category.setCategoryName(categoryName);
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long categoryId) throws EntityNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        List<Category> childCategories = categoryRepository.findAllByParentCategory_CategoryId(categoryId);

        for (Category childCategory : childCategories) {
            childCategory.setParentCategory(null);
        }

        categoryRepository.saveAll(childCategories);

        categoryRepository.deleteById(categoryId);
    }

}
