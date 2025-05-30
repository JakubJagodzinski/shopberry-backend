package com.example.shopberry.domain.favouriteentries.dto;

import com.example.shopberry.domain.favouriteentries.FavouriteEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouriteEntryDtoMapper {

    public FavouriteEntryResponseDto toDto(FavouriteEntry favouriteEntry) {
        FavouriteEntryResponseDto favouriteEntryResponseDto = new FavouriteEntryResponseDto();

        favouriteEntryResponseDto.setCustomerId(favouriteEntry.getCustomer().getId());
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
