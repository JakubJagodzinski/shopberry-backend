package com.example.internet_shop.favouriteentries;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouriteEntryDtoMapper {

    public FavouriteEntryDto toDto(FavouriteEntry favouriteEntry) {
        FavouriteEntryDto favouriteEntryDto = new FavouriteEntryDto();

        favouriteEntryDto.setCustomerId(favouriteEntry.getCustomer().getCustomerId());
        favouriteEntryDto.setProductId(favouriteEntry.getProduct().getProductId());
        favouriteEntryDto.setAddedAt(favouriteEntry.getAddedAt());

        return favouriteEntryDto;
    }

    public List<FavouriteEntryDto> toDtoList(List<FavouriteEntry> favouriteEntries) {
        return favouriteEntries.stream()
                .map(this::toDto)
                .toList();
    }

}
