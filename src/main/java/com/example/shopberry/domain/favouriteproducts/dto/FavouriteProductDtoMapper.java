package com.example.shopberry.domain.favouriteproducts.dto;

import com.example.shopberry.domain.favouriteproducts.FavouriteProduct;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavouriteProductDtoMapper {

    private final ProductDtoMapper productDtoMapper;

    public FavouriteProductResponseDto toDto(FavouriteProduct favouriteProduct) {
        FavouriteProductResponseDto favouriteProductResponseDto = new FavouriteProductResponseDto();

        favouriteProductResponseDto.setCustomerId(favouriteProduct.getCustomer().getUserId());
        favouriteProductResponseDto.setProduct(productDtoMapper.toDto(favouriteProduct.getProduct()));
        favouriteProductResponseDto.setAddedAt(favouriteProduct.getAddedAt());

        return favouriteProductResponseDto;
    }

    public List<FavouriteProductResponseDto> toDtoList(List<FavouriteProduct> favouriteProductList) {
        return favouriteProductList.stream()
                .map(this::toDto)
                .toList();
    }

}
