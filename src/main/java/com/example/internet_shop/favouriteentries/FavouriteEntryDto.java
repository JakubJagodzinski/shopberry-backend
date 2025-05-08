package com.example.internet_shop.favouriteentries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteEntryDto {

    private Long customerId;

    private Long productId;

    private LocalDateTime addedAt;

}
