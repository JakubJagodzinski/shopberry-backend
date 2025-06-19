package com.example.shopberry.domain.favouriteproducts.dto;

import com.example.shopberry.domain.favouriteproducts.FavouriteProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouriteProductDtoMapper {

    public FavouriteProductResponseDto toDto(FavouriteProduct favouriteProduct) {
        FavouriteProductResponseDto favouriteProductResponseDto = new FavouriteProductResponseDto();

        favouriteProductResponseDto.setCustomerId(favouriteProduct.getCustomer().getId());
        favouriteProductResponseDto.setProductId(favouriteProduct.getProduct().getProductId());
        favouriteProductResponseDto.setAddedAt(favouriteProduct.getAddedAt());

        return favouriteProductResponseDto;
    }

    public List<FavouriteProductResponseDto> toDtoList(List<FavouriteProduct> favouriteProductList) {
        return favouriteProductList.stream()
                .map(this::toDto)
                .toList();
    }

}
