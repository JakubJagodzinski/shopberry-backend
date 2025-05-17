package com.example.shopberry.cartentries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntryResponseDto {

    private Long customerId;

    private Long productId;

    private Long quantity;

    private LocalDateTime addedAt;

}
