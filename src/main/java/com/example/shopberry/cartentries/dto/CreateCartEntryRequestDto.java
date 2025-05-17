package com.example.shopberry.cartentries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartEntryRequestDto {

    private Long customerId;

    private Long productId;

    private Long quantity;

}
