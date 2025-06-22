package com.example.shopberry.domain.productattributes;

import com.example.shopberry.common.constants.messages.AttributeMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.productattributes.dto.*;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    private final ProductAttributeDtoMapper productAttributeDtoMapper;
    private final ProductDtoMapper productDtoMapper;
    private final AttributeDtoMapper attributeDtoMapper;

    @Transactional
    public ProductWithAttributesResponseDto getProductAllAttributes(Long productId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        ProductWithAttributesResponseDto productWithAttributesResponseDto = new ProductWithAttributesResponseDto();

        productWithAttributesResponseDto.setProduct(productDtoMapper.toDto(product));

        List<ProductAttribute> productAttributeDtoList = productAttributeRepository.findAllByProduct_ProductIdOrderByWeightDesc(productId);

        List<AttributeValueDto> attributeValueDtoList = new ArrayList<>();

        for (ProductAttribute productAttribute : productAttributeDtoList) {
            AttributeValueDto attributeValueDto = new AttributeValueDto();

            attributeValueDto.setAttribute(attributeDtoMapper.toDto(productAttribute.getAttribute()));
            attributeValueDto.setValue(productAttribute.getValue());
            attributeValueDto.setWeight(productAttribute.getWeight());

            attributeValueDtoList.add(attributeValueDto);
        }

        productWithAttributesResponseDto.setAttributes(attributeValueDtoList);

        return productWithAttributesResponseDto;
    }

    @Transactional
    public List<ProductAttributeResponseDto> getAllProductsWithAttribute(Long attributeId) throws EntityNotFoundException {
        if (!attributeRepository.existsById(attributeId)) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findAllByAttribute_AttributeId(attributeId));
    }

    @Transactional
    public ProductAttributeResponseDto assignAttributeToProduct(Long productId, AssignAttributeToProductRequestDto assignAttributeToProductRequestDto) throws EntityNotFoundException, EntityExistsException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Attribute attribute = attributeRepository.findById(assignAttributeToProductRequestDto.getAttributeId()).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(productId, attribute.getAttributeId());

        if (productAttributeRepository.existsById(productAttributeId)) {
            throw new EntityExistsException(AttributeMessages.ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_PRODUCT);
        }

        ProductAttribute productAttribute = new ProductAttribute();

        productAttribute.setId(productAttributeId);
        productAttribute.setProduct(product);
        productAttribute.setAttribute(attribute);
        productAttribute.setValue(assignAttributeToProductRequestDto.getValue());
        productAttribute.setWeight(assignAttributeToProductRequestDto.getWeight());

        return productAttributeDtoMapper.toDto(productAttributeRepository.save(productAttribute));
    }

    @Transactional
    public ProductAttributeResponseDto updateProductAttributeById(Long productId, Long attributeId, UpdateProductAttributeRequestDto updateProductAttributeRequestDto) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Attribute attribute = attributeRepository.findById(attributeId).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(productId, attributeId);

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId).orElse(null);

        if (productAttribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_ASSIGNED_TO_THIS_PRODUCT);
        }

        productAttribute.setValue(updateProductAttributeRequestDto.getValue());
        productAttribute.setWeight(updateProductAttributeRequestDto.getWeight());

        return productAttributeDtoMapper.toDto(productAttributeRepository.save(productAttribute));
    }

    @Transactional
    public void unassignAttributeFromProduct(Long productId, Long attributeId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Attribute attribute = attributeRepository.findById(attributeId).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(productId, attributeId);

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId).orElse(null);

        if (productAttribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_ASSIGNED_TO_THIS_PRODUCT);
        }

        productAttributeRepository.deleteById(productAttributeId);
    }

}
