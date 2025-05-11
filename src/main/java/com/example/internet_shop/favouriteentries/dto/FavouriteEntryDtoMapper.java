package com.example.internet_shop.favouriteentries.dto;

import com.example.internet_shop.favouriteentries.FavouriteEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouriteEntryDtoMapper {

    public FavouriteEntryResponseDto toDto(FavouriteEntry favouriteEntry) {
        FavouriteEntryResponseDto favouriteEntryResponseDto = new FavouriteEntryResponseDto();

        favouriteEntryResponseDto.setCustomerId(favouriteEntry.getCustomer().getCustomerId());
        favouriteEntryResponseDto.setProductId(favouriteEntry.getProduct().getProductId());
        favouriteEntryResponseDto.setAddedAt(favouriteEntry.getAddedAt());

        return favouriteEntryResponseDto;
    }

    public List<FavouriteEntryResponseDto> toDtoList(List<FavouriteEntry> favouriteEntries) {
        return favouriteEntries.stream()
                .map(this::toDto)
                .toList();
    }

}
