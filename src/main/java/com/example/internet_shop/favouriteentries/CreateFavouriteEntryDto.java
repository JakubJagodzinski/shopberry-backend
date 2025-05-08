package com.example.internet_shop.favouriteentries;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavouriteEntryDto {

    private Long customerId;

    private Long productId;

}
