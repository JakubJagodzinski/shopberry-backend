package com.example.internet_shop.products.dto;

import com.example.internet_shop.products.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDtoMapper {

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setDiscountPercentValue(product.getDiscountPercentValue());
        dto.setRatingValue(product.getRatingValue());
        dto.setRatingsCount(product.getRatingsCount());
        dto.setIsInStock(product.getIsInStock());
        dto.setImage(product.getImage());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }

}
