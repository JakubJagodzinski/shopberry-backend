package com.example.internet_shop.products;

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
    private final String PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "Product price must be greater than zero";
    private final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_NEGATIVE_MESSAGE = "Product discount percent value can't be negative";
    private final String PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_GREATER_THAN_100_MESSAGE = "Product discount percent value can't be greater than 100";

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public List<ProductDto> getProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductDto getProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productDtoMapper.toDto(productRepository.getReferenceById(id));
    }

    @Transactional
    public ProductDto createProduct(CreateProductDto createProductDto) throws IllegalStateException {
        if (productRepository.existsByProductName(createProductDto.getProductName())) {
            throw new IllegalStateException(PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        Product product = new Product();

        product.setProductName(createProductDto.getProductName());
        product.setProductPrice(createProductDto.getProductPrice());
        product.setIsInStock(createProductDto.getIsInStock());
        product.setImage(createProductDto.getImage());

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto editProductById(Long id, EditProductDto editProductDto) throws EntityNotFoundException, IllegalStateException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.getReferenceById(id);

        if (editProductDto.getProductName() != null) {
            Product otherProduct = productRepository.getProductByProductName(editProductDto.getProductName());

            if (otherProduct != null && !otherProduct.getProductId().equals(id)) {
                throw new IllegalStateException(PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            product.setProductName(editProductDto.getProductName());
        }

        if (editProductDto.getProductPrice() != null) {
            if (editProductDto.getProductPrice() <= 0) {
                throw new IllegalStateException(PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
            }

            product.setProductPrice(editProductDto.getProductPrice());
        }

        if (editProductDto.getDiscountPercentValue() != null) {
            if (editProductDto.getDiscountPercentValue() < 0) {
                throw new IllegalStateException(PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_NEGATIVE_MESSAGE);
            }
            if (editProductDto.getDiscountPercentValue() > 100) {
                throw new IllegalStateException(PRODUCT_DISCOUNT_PERCENT_VALUE_CAN_T_BE_GREATER_THAN_100_MESSAGE);
            }

            product.setDiscountPercentValue(editProductDto.getDiscountPercentValue());
        }

        if (editProductDto.getImage() != null) {
            product.setImage(editProductDto.getImage());
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
