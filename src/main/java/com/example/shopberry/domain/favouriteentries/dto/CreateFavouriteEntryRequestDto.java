package com.example.shopberry.domain.favouriteentries.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavouriteEntryRequestDto {

    private Long customerId;

    private Long productId;

}
