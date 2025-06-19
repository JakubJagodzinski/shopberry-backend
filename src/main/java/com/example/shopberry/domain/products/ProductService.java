package com.example.shopberry.domain.products;

import com.example.shopberry.domain.products.dto.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.products.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoMapper productDtoMapper;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Product with that name already exists";
    private static final String PRODUCT_NAME_CANNOT_BE_EMPTY_MESSAGE = "Product name cannot be empty";
    private static final String PRODUCT_NAME_CANNOT_BE_NULL_MESSAGE = "Product name cannot be null";
    private static final String PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "Product price must be greater than zero";
    private static final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_NEGATIVE_MESSAGE = "Product discount percent value can't be negative";
    private static final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_GREATER_THAN_100_MESSAGE = "Product discount percent value can't be greater than 100";

    public List<ProductResponseDto> getAllProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDto getProductById(Long id) throws EntityNotFoundException {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productDtoMapper.toDto(product);
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
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        if (updateProductRequestDto.getProductName() != null) {
            Product otherProduct = productRepository.findByProductName(updateProductRequestDto.getProductName()).orElse(null);

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

        if (updateProductRequestDto.getIsInStock() != null) {
            product.setIsInStock(updateProductRequestDto.getIsInStock());
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
