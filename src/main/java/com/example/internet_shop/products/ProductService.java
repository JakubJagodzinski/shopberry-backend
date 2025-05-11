package com.example.internet_shop.products;

import com.example.internet_shop.products.dto.CreateProductRequestDto;
import com.example.internet_shop.products.dto.ProductResponseDto;
import com.example.internet_shop.products.dto.ProductDtoMapper;
import com.example.internet_shop.products.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoMapper productDtoMapper;

    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Product with that name already exists";
    private final String PRODUCT_NAME_CANNOT_BE_EMPTY_MESSAGE = "Product name cannot be empty";
    private final String PRODUCT_NAME_CANNOT_BE_NULL_MESSAGE = "Product name cannot be null";
    private final String PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "Product price must be greater than zero";
    private final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_NEGATIVE_MESSAGE = "Product discount percent value can't be negative";
    private final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_GREATER_THAN_100_MESSAGE = "Product discount percent value can't be greater than 100";

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public List<ProductResponseDto> getProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDto getProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productDtoMapper.toDto(productRepository.getReferenceById(id));
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) throws IllegalArgumentException {
        if (productRepository.existsByProductName(createProductRequestDto.getProductName())) {
            throw new IllegalArgumentException(PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createProductRequestDto.getProductName() == null) {
            throw new IllegalArgumentException(PRODUCT_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createProductRequestDto.getProductName().isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        Product product = new Product();

        product.setProductName(createProductRequestDto.getProductName());
        product.setProductPrice(createProductRequestDto.getProductPrice());
        product.setIsInStock(createProductRequestDto.getIsInStock());
        product.setImage(createProductRequestDto.getImage());

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long id, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.getReferenceById(id);

        if (updateProductRequestDto.getProductName() != null) {
            Product otherProduct = productRepository.getProductByProductName(updateProductRequestDto.getProductName());

            if (otherProduct != null && !otherProduct.getProductId().equals(id)) {
                throw new IllegalArgumentException(PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            product.setProductName(updateProductRequestDto.getProductName());
        }

        if (updateProductRequestDto.getProductPrice() != null) {
            if (updateProductRequestDto.getProductPrice() <= 0) {
                throw new IllegalArgumentException(PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
            }

            product.setProductPrice(updateProductRequestDto.getProductPrice());
        }

        if (updateProductRequestDto.getDiscountPercentValue() != null) {
            if (updateProductRequestDto.getDiscountPercentValue() < 0) {
                throw new IllegalArgumentException(PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_NEGATIVE_MESSAGE);
            }
            if (updateProductRequestDto.getDiscountPercentValue() > 100) {
                throw new IllegalArgumentException(PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_GREATER_THAN_100_MESSAGE);
            }

            product.setDiscountPercentValue(updateProductRequestDto.getDiscountPercentValue());
        }

        if (updateProductRequestDto.getImage() != null) {
            product.setImage(updateProductRequestDto.getImage());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        productRepository.deleteById(id);
    }

}
