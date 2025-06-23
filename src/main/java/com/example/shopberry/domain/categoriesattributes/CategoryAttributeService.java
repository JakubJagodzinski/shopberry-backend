package com.example.shopberry.domain.categoriesattributes;

import com.example.shopberry.common.constants.messages.AttributeMessages;
import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.domain.categoriesattributes.dto.request.AssignAttributeToCategoryRequestDto;
import com.example.shopberry.domain.categoriesattributes.dto.CategoryAttributeDtoMapper;
import com.example.shopberry.domain.categoriesattributes.dto.response.CategoryAttributeResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAttributeService {

    private final CategoryAttributeRepository categoryAttributeRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    private final CategoryAttributeDtoMapper categoryAttributeDtoMapper;
    private final AttributeDtoMapper attributeDtoMapper;

    @Transactional
    public List<AttributeResponseDto> getCategoryAllAttributes(Long categoryId) throws EntityNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        List<CategoryAttribute> categoryAttributes = categoryAttributeRepository.findAllByCategory_CategoryId(categoryId);
        List<Attribute> attributes = categoryAttributes.stream()
                .map(CategoryAttribute::getAttribute)
                .toList();

        return attributeDtoMapper.toDtoList(attributes);
    }

    @Transactional
    public CategoryAttributeResponseDto assignAttributeToCategory(Long categoryId, AssignAttributeToCategoryRequestDto assignAttributeToCategoryRequestDto) throws EntityNotFoundException, EntityExistsException {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        Attribute attribute = attributeRepository.findById(assignAttributeToCategoryRequestDto.getAttributeId()).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        CategoryAttributeId categoryAttributeId = new CategoryAttributeId(categoryId, assignAttributeToCategoryRequestDto.getAttributeId());

        if (categoryAttributeRepository.existsById(categoryAttributeId)) {
            throw new EntityExistsException(AttributeMessages.ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_CATEGORY);
        }

        CategoryAttribute categoryAttribute = new CategoryAttribute();

        categoryAttribute.setId(categoryAttributeId);
        categoryAttribute.setCategory(category);
        categoryAttribute.setAttribute(attribute);

        return categoryAttributeDtoMapper.toDto(categoryAttributeRepository.save(categoryAttribute));
    }

    @Transactional
    public void unassignAttributeFromCategory(Long categoryId, Long attributeId) throws EntityNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        if (!attributeRepository.existsById(attributeId)) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        CategoryAttributeId categoryAttributeId = new CategoryAttributeId(categoryId, attributeId);

        if (!categoryAttributeRepository.existsById(categoryAttributeId)) {
            throw new EntityNotFoundException(CategoryMessages.ATTRIBUTE_NOT_ASSIGNED_TO_THIS_CATEGORY);
        }

        categoryAttributeRepository.deleteById(categoryAttributeId);
    }

}
