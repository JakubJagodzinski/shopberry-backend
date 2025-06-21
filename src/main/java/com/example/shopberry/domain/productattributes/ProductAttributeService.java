package com.example.shopberry.domain.productattributes;

import com.example.shopberry.common.constants.messages.AttributeMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.productattributes.dto.AssignAttributeToProductRequestDto;
import com.example.shopberry.domain.productattributes.dto.ProductAttributeDtoMapper;
import com.example.shopberry.domain.productattributes.dto.ProductAttributeResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityExistsException;
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

    @Transactional
    public List<ProductAttributeResponseDto> getProductAllAttributes(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findAllByProduct_ProductId(productId));
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
