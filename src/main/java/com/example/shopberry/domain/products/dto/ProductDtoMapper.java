package com.example.shopberry.domain.products.dto;

import com.example.shopberry.domain.producers.dto.ProducerDtoMapper;
import com.example.shopberry.domain.products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDtoMapper {

    private final ProducerDtoMapper producerDtoMapper;

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setDiscountPercentValue(product.getDiscountPercentValue());
        dto.setRatingValue(product.getRatingValue());
        dto.setRatingsCount(product.getRatingsCount());
        if (product.getProducer() != null) {
            dto.setProducer(producerDtoMapper.toDto(product.getProducer()));
        }
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setIsInStock(product.getIsInStock());
        dto.setImage(product.getImage());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> productList) {
        return productList.stream()
                .map(this::toDto)
                .toList();
    }

}
