package com.example.internet_shop.productattributes;

import com.example.internet_shop.attributes.Attribute;
import com.example.internet_shop.attributes.AttributeRepository;
import com.example.internet_shop.productattributes.dto.CreateProductAttributeRequestDto;
import com.example.internet_shop.productattributes.dto.ProductAttributeDtoMapper;
import com.example.internet_shop.productattributes.dto.ProductAttributeResponseDto;
import com.example.internet_shop.products.Product;
import com.example.internet_shop.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    private final ProductAttributeDtoMapper productAttributeDtoMapper;

    private final String PRODUCT_ATTRIBUTE_NOT_FOUND_MESSAGE = "Product attribute not found";
    private final String ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE = "Attribute already assigned to this product";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String ATTRIBUTE_NOT_FOUND_MESSAGE = "Attribute not found";
    private final String ATTRIBUTE_VALUE_CANNOT_BE_NULL_MESSAGE = "Attribute value cannot be null";

    public ProductAttributeService(ProductAttributeRepository productAttributeRepository, ProductRepository productRepository, AttributeRepository attributeRepository, ProductAttributeDtoMapper productAttributeDtoMapper) {
        this.productAttributeRepository = productAttributeRepository;
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
        this.productAttributeDtoMapper = productAttributeDtoMapper;
    }

    @Transactional
    public List<ProductAttributeResponseDto> getProductAttributesByProductId(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findById_ProductId(productId));
    }

    @Transactional
    public List<ProductAttributeResponseDto> getProductAttributesByAttributeId(Long attributeId) throws EntityNotFoundException {
        if (!attributeRepository.existsById(attributeId)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        return productAttributeDtoMapper.toDtoList(productAttributeRepository.findById_AttributeId(attributeId));
    }

    @Transactional
    public ProductAttributeResponseDto createProductAttribute(CreateProductAttributeRequestDto createProductAttributeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(createProductAttributeRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.findById(createProductAttributeRequestDto.getAttributeId()).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        ProductAttributeId productAttributeId = new ProductAttributeId(product.getProductId(), attribute.getAttributeId());

        if (productAttributeRepository.existsById(productAttributeId)) {
            throw new IllegalArgumentException(ATTRIBUTE_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE);
        }

        ProductAttribute productAttribute = new ProductAttribute();

        productAttribute.setId(productAttributeId);
        productAttribute.setProduct(product);
        productAttribute.setAttribute(attribute);

        if (createProductAttributeRequestDto.getValue() == null) {
            throw new IllegalArgumentException(ATTRIBUTE_VALUE_CANNOT_BE_NULL_MESSAGE);
        }

        productAttribute.setValue(createProductAttributeRequestDto.getValue());

        return productAttributeDtoMapper.toDto(productAttributeRepository.save(productAttribute));
    }

    @Transactional
    public void deleteProductAttributeById(ProductAttributeId productAttributeId) throws EntityNotFoundException {
        Product product = productRepository.findById(productAttributeId.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.findById(productAttributeId.getAttributeId()).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId).orElse(null);

        if (productAttribute == null) {
            throw new EntityNotFoundException(PRODUCT_ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        productAttributeRepository.deleteById(productAttributeId);
    }

}
