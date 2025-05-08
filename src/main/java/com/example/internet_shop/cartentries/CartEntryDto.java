package com.example.internet_shop.cartentries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntryDto {

    private Long customerId;

    private Long productId;

    private Long quantity;

    private LocalDateTime addedAt;

}
