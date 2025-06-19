package com.example.shopberry.domain.productattributes;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.productattributes.dto.AssignAttributeToProductRequestDto;
import com.example.shopberry.domain.productattributes.dto.ProductAttributeDtoMapper;
import com.example.shopberry.domain.productattributes.dto.ProductAttributeResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    private final ProductAttributeDtoMapper productAttributeDtoMapper;

    private static final String PRODUCT_ATTRIBUTE_NOT_FOUND_MESSAGE = "Product attribute not found";
    private static final String ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE = "Attribute already assigned to this product";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String ATTRIBUTE_NOT_FOUND_MESSAGE = "Attribute not found";
    private static final String ATTRIBUTE_VALUE_CANNOT_BE_NULL_MESSAGE = "Attribute value cannot be null";

    @Transactional
    public List<ProductAttributeResponseDto> getProductAllAttributes(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findById_ProductId(productId));
    }

    @Transactional
    public List<ProductAttributeResponseDto> getAllProductsWithAttribute(Long attributeId) throws EntityNotFoundException {
        if (!attributeRepository.existsById(attributeId)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findById_AttributeId(attributeId));
    }

    @Transactional
    public ProductAttributeResponseDto assignAttributeToProduct(Long productId, AssignAttributeToProductRequestDto assignAttributeToProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.findById(assignAttributeToProductRequestDto.getAttributeId()).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(productId, attribute.getAttributeId());

        if (productAttributeRepository.existsById(productAttributeId)) {
            throw new IllegalArgumentException(ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE);
        }

        ProductAttribute productAttribute = new ProductAttribute();

        productAttribute.setId(productAttributeId);
        productAttribute.setProduct(product);
        productAttribute.setAttribute(attribute);

        if (assignAttributeToProductRequestDto.getValue() == null) {
            throw new IllegalArgumentException(ATTRIBUTE_VALUE_CANNOT_BE_NULL_MESSAGE);
        }

        productAttribute.setValue(assignAttributeToProductRequestDto.getValue());

        return productAttributeDtoMapper.toDto(productAttributeRepository.save(productAttribute));
    }

    @Transactional
    public void unassignAttributeFromProduct(Long productId, Long attributeId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.findById(attributeId).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(productId, attributeId);

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId).orElse(null);

        if (productAttribute == null) {
            throw new EntityNotFoundException(PRODUCT_ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        productAttributeRepository.deleteById(productAttributeId);
    }

}
