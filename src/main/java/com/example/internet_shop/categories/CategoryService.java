package com.example.internet_shop.categories;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;

    private final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    private final String PARENT_CATEGORY_NOT_FOUND_MESSAGE = "Parent category not found";
    private final String CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Category with that name already exists";
    private final String CATEGORY_CANNOT_BE_PARENT_TO_ITSELF_MESSAGE = "Category cannot be parent to itself";

    public CategoryService(CategoryRepository categoryRepository, CategoryDtoMapper categoryDtoMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    public List<CategoryDto> getCategories() {
        return categoryDtoMapper.toDtoList(categoryRepository.findAll());
    }

    @Transactional
    public CategoryDto getCategoryById(Long id) throws EntityNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        return categoryDtoMapper.toDto(categoryRepository.getReferenceById(id));
    }

    @Transactional
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) throws IllegalStateException {
        if (categoryRepository.existsByCategoryName(createCategoryDto.getCategoryName())) {
            throw new IllegalStateException(CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        Category category = new Category();

        category.setCategoryName(createCategoryDto.getCategoryName());

        Long parentCategoryId = createCategoryDto.getParentCategoryId();

        if (parentCategoryId != null) {
            if (!categoryRepository.existsById(parentCategoryId)) {
                throw new IllegalStateException(PARENT_CATEGORY_NOT_FOUND_MESSAGE);
            }

            Category parentCategory = categoryRepository.getReferenceById(parentCategoryId);
            category.setParentCategory(parentCategory);
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto setParentCategory(Long childCategoryId, Long parentCategoryId) throws EntityNotFoundException, IllegalStateException {
        if (!categoryRepository.existsById(childCategoryId)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        if (!categoryRepository.existsById(parentCategoryId)) {
            throw new IllegalStateException(PARENT_CATEGORY_NOT_FOUND_MESSAGE);
        }

        if (parentCategoryId.equals(childCategoryId)) {
            throw new IllegalStateException(CATEGORY_CANNOT_BE_PARENT_TO_ITSELF_MESSAGE);
        }

        Category childCategory = categoryRepository.getReferenceById(childCategoryId);
        Category parentCategory = categoryRepository.getReferenceById(parentCategoryId);

        childCategory.setParentCategory(parentCategory);

        return categoryDtoMapper.toDto(categoryRepository.save(childCategory));
    }

    @Transactional
    public CategoryDto updateCategoryById(Long id, UpdateCategoryDto updateCategoryDto) throws EntityNotFoundException, IllegalStateException {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        Category category = categoryRepository.getReferenceById(id);

        if (updateCategoryDto.getCategoryName() != null) {
            Category otherCategory = categoryRepository.findByCategoryName(updateCategoryDto.getCategoryName());

            if (otherCategory != null && !otherCategory.getCategoryId().equals(id)) {
                throw new IllegalStateException(CATEGORY_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            category.setCategoryName(updateCategoryDto.getCategoryName());
        }

        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long id) throws EntityNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        categoryRepository.deleteById(id);
    }

}
