package com.example.shopberry.favouriteentries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteEntryResponseDto {

    private Long customerId;

    private Long productId;

    private LocalDateTime addedAt;

}
